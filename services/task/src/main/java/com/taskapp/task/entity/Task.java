package com.taskapp.task.entity;

import com.taskapp.task.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_userId", columnList = "userId", unique = true)
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(unique = true)
    private String username;
}

