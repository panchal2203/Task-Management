package com.taskapp.task.service;

import com.taskapp.task.dto.TaskDTO;
import com.taskapp.task.entity.Task;

import java.util.List;


public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
    List<TaskDTO> getTasksByUsername(String username);
    Task markTaskAsComplete(Long taskId);
}


