package com.taskapp.task.service;


import com.taskapp.task.constant.Authority;
import com.taskapp.task.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String userServiceUrl = "http://localhost:8080/users/username" + username;
            ResponseEntity<LoginRequest> response = new RestTemplate().getForEntity(userServiceUrl, LoginRequest.class);
            LoginRequest loginRequest = response.getBody();
            if (loginRequest != null) {
                return new org.springframework.security.core.userdetails.User(loginRequest.getUsername(), loginRequest.getPassword(),
                        AuthorityUtils.createAuthorityList(Authority.ROLE_USER));
            }
        } catch (ResourceAccessException e) {
            System.out.println("User service is unavailable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new org.springframework.security.core.userdetails.User(username, "",
                AuthorityUtils.createAuthorityList(Authority.ROLE_USER));
    }

}
