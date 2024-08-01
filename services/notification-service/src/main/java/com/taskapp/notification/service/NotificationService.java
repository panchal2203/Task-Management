package com.taskapp.notification.service;



import com.taskapp.notification.dto.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    public void sendTaskNotification(MessageRequest messageRequest) {
        // Implement the logic to send SMS using a third-party service
        // For example, you might use Twilio, Nexmo, or another SMS provider
        // This is a simplified example
    }
}

