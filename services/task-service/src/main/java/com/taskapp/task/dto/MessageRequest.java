package com.taskapp.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String emailId;
    private String messageHeader;
    private String messageContent;
}
