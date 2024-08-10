package com.taskapp.user.service;

import com.taskapp.user.dto.ApiResponse;
import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.entity.User;
import com.taskapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserSuccess(){

        //given
        User user= new User();
        user.setId(1L);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("pradeep1");
        userDTO.setPassword("password1");
        userDTO.setEmail("pradeep@gmail.com");

        //when
        ApiResponse<?> registerUser = userService.registerUser(userDTO);

        //then
        assertTrue(registerUser.getMessage().contains("User registered successfully with userId ->"));
        assertTrue(registerUser.isSuccess());

    }

    @Test
    public void testRegisterUserUsernameExists(){

        //given
        User user= new User();
        user.setId(1L);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("pradeep1");
        userDTO.setPassword("password1");
        userDTO.setEmail("pradeep@gmail.com");

        //when
        ApiResponse<?> registerUser = userService.registerUser(userDTO);

        //then
        assertEquals("Username already exists.", registerUser.getMessage());
        assertFalse(registerUser.isSuccess());
    }

    @Test
    public void testRegisterUserEmailExists(){

        //given
        User user= new User();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("pradeep1");
        userDTO.setPassword("password1");
        userDTO.setEmail("pradeep@gmail.com");

        //when
        ApiResponse<?> registerUser = userService.registerUser(userDTO);

        //then
        assertEquals("Email already exists.", registerUser.getMessage());
        assertFalse(registerUser.isSuccess());
    }

}
