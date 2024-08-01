package com.taskapp.task.dto;

import com.taskapp.task.Enum.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String creationDate;
    private String dueDate;
    private Long userId;

}
