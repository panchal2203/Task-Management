package com.taskapp.task.service;

import com.taskapp.task.Enum.TaskStatus;
import com.taskapp.task.dto.TaskDTO;
import com.taskapp.task.entity.Task;
import com.taskapp.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private KafkaTemplate<String, TaskDTO> kafkaTemplate;

    private static final String TASK_TOPIC = "task-events";

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task taskEntity = new Task();
        taskEntity.setTitle(taskDTO.getTitle());
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setStatus(taskDTO.getStatus());
        taskEntity.setCreationDate(LocalDateTime.now());
        taskEntity.setUsername(taskDTO.getUsername());

        Task savedEntity = taskRepository.save(taskEntity);
        taskDTO.setId(savedEntity.getId());

        kafkaTemplate.send(TASK_TOPIC, taskDTO);

        return taskDTO;
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskEntity.setTitle(taskDTO.getTitle());
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setStatus(taskDTO.getStatus());

        Task updatedEntity = taskRepository.save(taskEntity);
        taskDTO.setId(updatedEntity.getId());

        kafkaTemplate.send(TASK_TOPIC, taskDTO);

        return taskDTO;
    }
    public void deleteTask(Long id) {
        Task taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(taskEntity);
    }

    @Override
    public List<TaskDTO> getTasksByUsername(String username) {
        return taskRepository.findByUsername(username).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Task markTaskAsComplete(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }

    private TaskDTO toDTO(Task taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setTitle(taskEntity.getTitle());
        taskDTO.setDescription(taskEntity.getDescription());
        taskDTO.setStatus(taskEntity.getStatus());
        taskDTO.setCreationDate(taskEntity.getCreationDate());
        taskDTO.setUsername(taskEntity.getUsername());
        return taskDTO;
    }

}
