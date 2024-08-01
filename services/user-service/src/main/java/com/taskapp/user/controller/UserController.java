package com.taskapp.user.controller;

import com.taskapp.user.dto.ApiResponse;
import com.taskapp.user.dto.LoginRequest;
import com.taskapp.user.dto.LoginResponse;
import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.security.JwtUtil;
import com.taskapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserDTO userDTO) {
        ApiResponse<?> response = userService.registerUser(userDTO);
        if (response.isSuccess()) {
            try {
                String token = getJwtToken(userDTO.getUsername(), userDTO.getPassword());
                response = new ApiResponse<>(true, "User registered successfully.", new LoginResponse(token));
            } catch (Exception e) {
                response = new ApiResponse<>(false, e.getMessage());
            }
        }
        return ResponseEntity.ok(response);

    }

    private String getJwtToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
//                      ,Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response;
        try {
            String token = getJwtToken(loginRequest.getUsername(), loginRequest.getPassword());
            response = new ApiResponse<>(true, "User logged in successfully", new LoginResponse(token));
        } catch (Exception e) {
            response = new ApiResponse<>(false, e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        ApiResponse<UserDTO> response = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        ApiResponse<UserDTO> response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        ApiResponse<UserDTO> response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        ApiResponse<?> response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

}
