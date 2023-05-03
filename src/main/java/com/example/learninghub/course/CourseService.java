package com.example.learninghub.course;

import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourse(Integer id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Problem> getCourseProblems(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow();
        return course.getProblems().stream().sorted((x, y) -> {
            if (x.getOrdinalNumber() < y.getOrdinalNumber()) {
                return -1;
            } else if (x.getOrdinalNumber() > y.getOrdinalNumber()) {
                return 1;
            }
            return 0;
        }).map(CourseProblem::getProblem).toList();
    }
}
