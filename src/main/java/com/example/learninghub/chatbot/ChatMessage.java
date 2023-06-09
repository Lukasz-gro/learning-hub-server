package com.example.learninghub.chatbot;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "ChatMessage")
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private Date date;
    @Column(name = "is_user_message")
    private Boolean isUserMessage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude private User user;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    @ToString.Exclude private Problem problem;

    public ChatMessage(String message, Date date, Boolean isUserMessage, User user, Problem problem) {
        this.message = message;
        this.date = date;
        this.isUserMessage = isUserMessage;
        this.user = user;
        this.problem = problem;
    }
}
