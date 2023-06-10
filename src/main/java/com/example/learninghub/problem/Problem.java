package com.example.learninghub.problem;

import com.example.learninghub.chatbot.ChatMessage;
import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.hint.Hint;
import com.example.learninghub.submit.Submit;
import com.example.learninghub.tag.Tag;
import com.example.learninghub.test.Test;
import com.example.learninghub.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
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
    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    @ToString.Exclude private Set<ChatMessage> chatMessages;

    protected Problem(Problem problem) {
        this.id = problem.id;
        this.name = problem.name;
        this.description = problem.description;
        this.prompt = problem.prompt;
        this.courses = problem.courses;
        this.tags = problem.tags;
        this.hints = problem.hints;
        this.tests = problem.tests;
        this.submits = problem.submits;
        this.chatMessages = problem.chatMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Problem)) {
            return false;
        }
        Problem other = (Problem) o;
        return this.id.equals(other.id);
    }
}
