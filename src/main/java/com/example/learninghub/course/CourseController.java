package com.example.learninghub.course;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.UserProblem;
import jakarta.servlet.http.HttpServletRequest;
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
    public Course getCourseAuthorized(@PathVariable("courseId") Integer courseId) {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/v1/auth/course/all")
    public List<Course> getAllCoursesAuthorized() {
        return courseService.getAllCourses();
    }

    @GetMapping("/v1/auth/course/{courseId}/problems")
    public List<Problem> getCourseProblemsAuthorized(@PathVariable("courseId") Integer courseId) {
        return courseService.getCourseProblems(courseId);
    }

    @GetMapping("/v1/course/{courseId}")
    public Course getCourse(@PathVariable("courseId") Integer courseId, HttpServletRequest request) {
        if (courseService.authenticate(request, courseId)) {
            return courseService.getCourse(courseId);
        }
        return null;
    }

    @GetMapping("/v1/course/all")
    public List<Course> getAllCourses(HttpServletRequest request) {
        return courseService.getAllUserCourses(request);
    }

    @GetMapping("/v1/course/{courseId}/problems")
    public List<UserProblem> getCourseProblems(@PathVariable("courseId") Integer courseId, HttpServletRequest request) {
        if (courseService.authenticate(request, courseId)) {
            return courseService.getCourseUserProblems(request, courseId);
        }
        return null;
    }

}
