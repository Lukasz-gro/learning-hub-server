package com.example.learninghub.chatbot;

import lombok.Getter;

@Getter
public class AddMessageRequest {
    private String username;
    private Integer problemId;
    private String message;
    private Boolean isUser;
    private String messageType;
}
