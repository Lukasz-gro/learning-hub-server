package com.example.learninghub.course;

import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/v1/auth/course/{courseId}")
    public Course getCourse(@PathVariable("courseId") Integer id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/v1/auth/course/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/v1/auth/course/{courseId}/problems")
    public List<Problem> getCourseProblems(@PathVariable("courseId") Integer id) {
        return courseService.getCourseProblems(id);
    }

}
