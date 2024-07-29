package com.taskapp.notification.listner;


import com.taskapp.notification.dto.TaskDTO;
import com.taskapp.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventsListener {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "${kafka.topic.task-events}", groupId = "group_id")
    public void listen(TaskDTO taskDTO) {
        notificationService.sendTaskNotification(taskDTO);
    }
}

