package com.taskapp.user.dto;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<TaskDTO> taskDTOList;
}
