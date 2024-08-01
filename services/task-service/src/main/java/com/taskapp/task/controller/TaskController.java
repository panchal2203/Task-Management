package com.taskapp.task.controller;

import com.taskapp.task.dto.ApiResponse;
import com.taskapp.task.dto.TaskDTO;
import com.taskapp.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody TaskDTO taskDTO) {
        ApiResponse<TaskDTO> response = taskService.createTask(taskDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        ApiResponse<TaskDTO> response = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable Long id) {
        ApiResponse<?> response = taskService.deleteTask(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTasksById(@RequestParam Long taskId) {
        ApiResponse<TaskDTO> response = taskService.getTasksId(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allTasks")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByUserId(@RequestParam Long userId) {
        ApiResponse<List<TaskDTO>> response = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<ApiResponse<?>> markTaskAsComplete(@PathVariable Long taskId) {
        ApiResponse<?> response = taskService.markTaskAsComplete(taskId);
        return ResponseEntity.ok(response);
    }

}

