package com.taskapp.notification.service;



import com.taskapp.notification.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTaskNotification(TaskDTO taskDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(taskDTO.getEmail());
        message.setSubject("Task Notification: " + taskDTO.getTitle());
        message.setText("Task Details:\n" + "Title: " + taskDTO.getTitle() + "\nDescription: " + taskDTO.getDescription());
        mailSender.send(message);
    }
}

