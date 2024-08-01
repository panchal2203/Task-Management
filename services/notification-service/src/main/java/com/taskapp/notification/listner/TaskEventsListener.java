package com.taskapp.notification.listner;

import com.taskapp.notification.dto.MessageRequest;
import com.taskapp.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventsListener {

    @Autowired
    private NotificationService notificationService;

    private static final String TASK_TOPIC = "task-events";

    private static final String GROUP_ID = "group_id";

    @KafkaListener(topics = TASK_TOPIC, groupId = GROUP_ID)
    public void listen(String messageRequest) {
        notificationService.sendTaskNotification(messageRequest);
    }
}

