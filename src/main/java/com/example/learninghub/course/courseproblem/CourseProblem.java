package com.example.learninghub.course.courseproblem;

import com.example.learninghub.course.Course;
import com.example.learninghub.problem.Problem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Entity(name = "CourseProblem")
@Table(name = "course_problem")
public class CourseProblem {

    @EmbeddedId
    CourseProblemKey id;
    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @MapsId("problemId")
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @Column(name = "ordinal_number")
    private Integer ordinalNumber;
}
