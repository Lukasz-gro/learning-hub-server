package com.example.learninghub.submit;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Submit")
@Table(name = "submit")
public class Submit {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "output")
    private String output;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name = "date")
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    @ToString.Exclude private Problem problem;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude private User user;

}
