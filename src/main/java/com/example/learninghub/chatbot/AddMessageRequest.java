package com.example.learninghub.chatbot;

import lombok.Data;

@Data
public class AddMessageRequest {
    private String username;
    private Integer problemId;
    private String message;
    private Boolean isUserMessage;
}
