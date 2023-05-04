package com.example.learninghub.course;

import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{courseId}")
    public Course getCourse(@PathVariable("courseId") Integer id) {
        return courseService.getCourse(id);
    }

    @GetMapping("all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("{courseId}/problems")
    public List<Problem> getCourseProblems(@PathVariable("courseId") Integer id) {
        return courseService.getCourseProblems(id);
    }

}
