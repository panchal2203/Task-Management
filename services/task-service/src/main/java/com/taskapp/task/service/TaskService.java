package com.taskapp.task.service;

import com.taskapp.task.dto.ApiResponse;
import com.taskapp.task.dto.TaskDTO;

import java.util.List;


public interface TaskService {

    ApiResponse<TaskDTO> createTask(TaskDTO taskDTO);

    ApiResponse<TaskDTO> updateTask(Long id, TaskDTO taskDTO);

    ApiResponse<?> deleteTask(Long id);

    ApiResponse<List<TaskDTO>> getTasksByUserId(Long userId);

    ApiResponse<?> markTaskAsComplete(Long taskId);

    ApiResponse<TaskDTO> getTasksId(Long taskId);
}


