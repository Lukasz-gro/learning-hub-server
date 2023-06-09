package com.example.learninghub.course;

import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@ToString
@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    @ToString.Exclude private Set<CourseProblem> problems;
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    @ToString.Exclude private Set<User> users;

}
