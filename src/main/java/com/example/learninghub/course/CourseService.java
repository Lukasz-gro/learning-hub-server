package com.example.learninghub.course;

import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import com.example.learninghub.problem.UserProblem;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserRepository;
import com.example.learninghub.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ProblemService problemService;

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

    public boolean authenticate(HttpServletRequest request, Integer courseId) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getCourses().stream().anyMatch(course -> course.getId().equals(courseId));
    }

    public List<Course> getAllUserCourses(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getCourses().stream().toList();
    }

    public List<UserProblem> getCourseUserProblems(HttpServletRequest request, Integer courseId) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Course course = user.getCourses().stream().filter(c -> c.getId().equals(courseId)).toList().get(0);
        return course.getProblems().stream()
                .map(courseProblem -> problemService.getUserProblem(user, courseProblem.getProblem()))
                .collect(Collectors.toList());
    }
}
