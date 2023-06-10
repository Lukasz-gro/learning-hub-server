package com.example.learninghub.test;

import com.example.learninghub.problem.Problem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity(name = "Test")
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "input")
    private String input;
    @Column(name = "output")
    private String output;
    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    @ToString.Exclude private Problem problem;

}
