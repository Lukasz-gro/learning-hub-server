package com.example.learninghub.user;

import com.example.learninghub.course.Course;
import com.example.learninghub.course.CourseRepository;
import com.example.learninghub.course.CourseService;
import com.example.learninghub.course.courseproblem.CourseProblem;
import com.example.learninghub.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public boolean hasAccess(String username, Problem problem) {
        User user = getUser(username);
        for (Course course : user.getCourses()) {
            for (Problem p : course.getProblems().stream().map(CourseProblem::getProblem).toList()) {
                if (p.equals(problem)) {
                    return true;
                }
            }
        }
        return false;
    }
}
