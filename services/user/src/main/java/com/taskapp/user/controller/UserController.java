package com.taskapp.user.controller;

import com.taskapp.user.dto.LoginRequest;
import com.taskapp.user.dto.LoginResponse;
import com.taskapp.user.dto.TaskDTO;
import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.security.JwtTokenProvider;
import com.taskapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO, userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registeredUser.getUsername(),
                        userDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/userdetails")
    public ResponseEntity<?> getUserDetails(@RequestParam String username) {
        UserDTO userDTO = userService.getUserDetails(username);
        // Assuming TaskService and its methods are defined elsewhere
//        List<TaskDTO> tasks = taskService.getTasksByUserId(userDTO.getId());
//        userDTO.setTasks(tasks);
        return ResponseEntity.ok(userDTO);
    }
}
