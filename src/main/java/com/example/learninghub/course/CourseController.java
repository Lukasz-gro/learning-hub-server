package com.example.learninghub.course;

import com.example.learninghub.problem.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/v1/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable("courseId") Integer id) {
        try {
            Course course = courseService.getCourse(id);
            return ResponseEntity.ok(course);
        } catch (NoSuchFieldError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("{courseId}/problems")
    public ResponseEntity<List<Problem>> getCourseProblems(@PathVariable("courseId") Integer id) {
        try {
            List<Problem> problems = courseService.getCourseProblems(id);
            return ResponseEntity.ok(problems);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
