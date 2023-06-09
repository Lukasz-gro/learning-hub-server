package com.example.learninghub.chatmessage;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean is_user_message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude private User user;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    @ToString.Exclude private Problem problem;


}
