package com.taskapp.task.service;


import com.taskapp.task.constant.Authority;
import org.springframework.http.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class CustomUserDetailsService {

    public UserDetails loadUserByUsername(String username, String authToken) throws UsernameNotFoundException {
        try {
            String userServiceUrl = "http://user-service-container:8080/users/username/" + username;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Object> response = new RestTemplate().exchange(userServiceUrl, HttpMethod.GET, entity, Object.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return new org.springframework.security.core.userdetails.User(username, "", AuthorityUtils.createAuthorityList(Authority.ROLE_USER));
            }
        } catch (ResourceAccessException e) {
            System.out.println("User service is unavailable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
