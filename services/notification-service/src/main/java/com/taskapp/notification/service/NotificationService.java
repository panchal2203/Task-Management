package com.taskapp.notification.service;


import com.taskapp.notification.entity.MessageLog;
import com.taskapp.notification.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {


    @Autowired
    private MessageRepository messageRepository;

    public void sendTaskNotification(String messageRequest) {
        // Implement the logic to send SMS using a third-party service
        // For example, you might use Twilio, Nexmo, or another SMS provider
        // This is a simplified example

        MessageLog messageLog = new MessageLog();
        messageLog.setSentOn(LocalDateTime.now());
        messageLog.setMessageContent(messageRequest);
        messageRepository.save(messageLog);
    }
}

