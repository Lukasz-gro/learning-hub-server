package com.example.learninghub.problem;

import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.test.Test;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@ToString
@Entity(name = "Problem")
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<Test> tests;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<CourseProblem> courses;

}
