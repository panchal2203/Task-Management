package com.taskapp.task.entity;

import com.taskapp.task.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_userid", columnList = "userId")
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private Long userId;
}

