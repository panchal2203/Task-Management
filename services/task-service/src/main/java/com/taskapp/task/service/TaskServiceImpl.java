package com.taskapp.task.service;

import com.taskapp.task.Enum.TaskStatus;
import com.taskapp.task.dto.ApiResponse;
import com.taskapp.task.dto.MessageRequest;
import com.taskapp.task.dto.TaskDTO;
import com.taskapp.task.entity.Task;
import com.taskapp.task.repository.TaskRepository;
import com.taskapp.task.util.DateUtility;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TASK_TOPIC = "task-events";

    public ApiResponse<?> createTask(TaskDTO taskDTO) {
        try {
            Task taskEntity = new Task();
            taskEntity.setTitle(taskDTO.getTitle());
            taskEntity.setDescription(taskDTO.getDescription());
            taskEntity.setStatus(TaskStatus.PENDING);
            taskEntity.setCreationDate(LocalDate.now());
            taskEntity.setUserId(taskDTO.getUserId());
            taskEntity.setDueDate(DateUtility.stringToLocalDate(taskDTO.getDueDate()));
            taskRepository.save(taskEntity);
            sendNotification("Task created successfully -> " + taskDTO.getTitle());
            return new ApiResponse<>(true, "Task created successfully.");
        } catch (KafkaException exception){
            return new ApiResponse<>(true, "Task created successfully but failed to send notification.");
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    public ApiResponse<?> updateTask(Long id, TaskDTO taskDTO) {

        try {
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (taskOptional.isPresent()) {
                Task taskEntity = taskOptional.get();
                taskEntity.setTitle(taskDTO.getTitle());
                taskEntity.setDescription(taskDTO.getDescription());
                taskEntity.setStatus(taskDTO.getStatus());
                taskEntity.setDueDate(DateUtility.stringToLocalDate(taskDTO.getDueDate()));
                taskRepository.save(taskEntity);
                sendNotification("Task updated successfully -> " + taskDTO.getTitle());
                return new ApiResponse<>(true, "Task updated successfully.");
            } else {
                return new ApiResponse<>(false, "Task with given taskId is not present.");
            }
        } catch (KafkaException exception){
            return new ApiResponse<>(true, "Task updated successfully but failed to send notification.");
        }  catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    public ApiResponse<?> deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResponse<>(true, "Task deleted successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTasksByUserId(Long userId) {
        try {
            List<TaskDTO> taskDTOList = taskRepository.findByUserId(userId).stream().map(this::toDTO).toList();
            return new ApiResponse<>(true, "Success.", taskDTOList);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }


    @Override
    public ApiResponse<?> markTaskAsComplete(Long taskId) {
        try {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Task taskEntity = taskOptional.get();
                taskEntity.setStatus(TaskStatus.COMPLETED);
                taskRepository.save(taskEntity);
                sendNotification("Task completed successfully -> " + taskEntity.getTitle());
                return new ApiResponse<>(true, "Task completed successfully.");
            } else {
                return new ApiResponse<>(false, "Task with given taskId is not present.");
            }
        } catch (KafkaException exception){
            return new ApiResponse<>(true, "Task completed successfully but failed to send notification.");
        }  catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse<TaskDTO> getTasksId(Long taskId) {
        try {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Task taskEntity = taskOptional.get();
                return new ApiResponse<>(true, "Success.", toDTO(taskEntity));
            } else {
                return new ApiResponse<>(false, "Task with given taskId is not present.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }

    private TaskDTO toDTO(Task taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setTitle(taskEntity.getTitle());
        taskDTO.setDescription(taskEntity.getDescription());
        taskDTO.setStatus(taskEntity.getStatus());
        taskDTO.setCreationDate(DateUtility.localDateToString(taskEntity.getCreationDate()));
        taskDTO.setUserId(taskEntity.getUserId());
        taskDTO.setDueDate(DateUtility.localDateToString(taskEntity.getDueDate()));
        return taskDTO;
    }

    private void sendNotification(String message){
        MessageRequest messageRequest = new MessageRequest("praddeppanchal@gmail.com", message);
        kafkaTemplate.send(TASK_TOPIC, messageRequest.toString());
    }

}
