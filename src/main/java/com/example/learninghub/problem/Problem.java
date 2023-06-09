package com.example.learninghub.problem;

import com.example.learninghub.chatbot.ChatMessage;
import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.hint.Hint;
import com.example.learninghub.submit.Submit;
import com.example.learninghub.tag.Tag;
import com.example.learninghub.test.Test;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
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
    @Column(name = "prompt")
    private String prompt;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<CourseProblem> courses;
    @ManyToMany
    @JoinTable(
            name = "problem_tag",
            joinColumns = @JoinColumn(name = "problem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnore
    @ToString.Exclude private Set<Tag> tags;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<Hint> hints;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<Test> tests;
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<Submit> submits;
    @OneToMany
    @JsonIgnore
    @ToString.Exclude private Set<ChatMessage> chatMessages;

}
