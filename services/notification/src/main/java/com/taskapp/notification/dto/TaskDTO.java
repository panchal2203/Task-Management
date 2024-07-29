package com.taskapp.notification.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private String username;
    private String email;
}
