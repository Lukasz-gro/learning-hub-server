package com.example.learninghub.chatbot;

import lombok.Data;

@Data
public class AddMessageRequest {
    private Integer userId;
    private Integer problemId;
    private String message;
    private Boolean isUserMessage;
}
