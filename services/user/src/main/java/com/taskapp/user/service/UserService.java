package com.taskapp.user.service;


import com.taskapp.user.dto.UserDTO;
import com.taskapp.user.entity.User;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO, String rawPassword);
    UserDTO getUserDetails(String username);
}
