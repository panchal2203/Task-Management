package com.taskapp.user.service;


import com.taskapp.user.dto.ApiResponse;
import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.entity.User;

import java.util.List;

public interface UserService {
    ApiResponse<?> registerUser(UserDTO userDTO);

    ApiResponse<UserDTO> getUserById(Long id);

    ApiResponse<UserDTO> updateUser(Long id, UserDTO userDTO);

    ApiResponse<?> deleteUser(Long id);

    ApiResponse<UserDTO> getUserByUsername(String username);
}
