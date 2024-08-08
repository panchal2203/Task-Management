package com.taskapp.user.service;

import com.taskapp.user.constant.Authority;
import com.taskapp.user.dto.ApiResponse;
import com.taskapp.user.dto.TaskDTO;
import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.entity.User;
import com.taskapp.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public ApiResponse<?> registerUser(UserDTO userDTO) {
        try {
            String errorMessage = null;
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
                errorMessage = "Username already exists.";
            } else if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                errorMessage = "Email already exists";
            }
            if (errorMessage == null) {
                User user = new User();
                user.setUsername(userDTO.getUsername());
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setEmail(userDTO.getEmail());
                user.setAuthority(Authority.ROLE_USER);
                userRepository.save(user);
                return new ApiResponse<>(true, "User registered successfully with userId -> " + user.getId());
            } else {
                return new ApiResponse<>(false, errorMessage);
            }
        } catch (Exception exception) {
            return new ApiResponse<>(false, exception.getMessage());
        }
    }


    public ApiResponse<UserDTO> updateUser(Long userId, UserDTO userDTO) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(userId);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                if (userDTO.getEmail() != null) {
                    existingUser.setEmail(userDTO.getEmail());
                }
                if (userDTO.getPassword() != null) {
                    existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
                userRepository.save(existingUser);
                return new ApiResponse<>(true, "User updated successfully", userDTO);
            } else {
                return new ApiResponse<>(false, "UserId does not exists.");
            }
        } catch (Exception exception) {
            return new ApiResponse<>(false, exception.getMessage());
        }

    }

    public ApiResponse<?> deleteUser(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                userRepository.delete(userOptional.get());
                return new ApiResponse<>(true, "User deleted successfully.");
            } else {
                return new ApiResponse<>(false, "UserId does not exists.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    public ApiResponse<UserDTO> getUserById(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                return new ApiResponse<>(true, "Success.", userDTO);
            } else {
                return new ApiResponse<>(false, "UserId does not exists.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    public ApiResponse<UserDTO> getUserByUsername(String username) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setTaskDTOList(getTasksByUserId(user.getId()));
                return new ApiResponse<>(true, "Success.", userDTO);
            } else {
                return new ApiResponse<>(false, "Username does not exists.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    private List<TaskDTO> getTasksByUserId(Long userId) {
        try {
            String taskServiceUrl = "http://localhost:8081/tasks/allTasks?userId=" + userId;
            ResponseEntity<TaskDTO[]> response = new RestTemplate().getForEntity(taskServiceUrl, TaskDTO[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (ResourceAccessException e) {
            // Task service is unavailable
            System.out.println("Task service is unavailable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}

