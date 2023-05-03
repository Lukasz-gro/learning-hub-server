package com.example.learninghub.course.courseproblem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CourseProblemKey implements Serializable {

    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "problem_id")
    private Integer problemId;
}
