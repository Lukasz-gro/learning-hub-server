package com.example.learninghub.problem;

import com.example.learninghub.submit.Submit;
import com.example.learninghub.submit.SubmitService;
import com.example.learninghub.user.User;
import com.example.learninghub.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserService userService;

    @Autowired
    public ProblemService(ProblemRepository problemRepository, UserService userService) {
        this.problemRepository = problemRepository;
        this.userService = userService;
    }

    public Problem getProblem(Integer problemId) {
        return problemRepository.findById(problemId).orElseThrow();
    }

    public UserProblem getUserProblem(HttpServletRequest request, Integer problemId) {
        Problem problem = getProblem(problemId);
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userService.getUser(username);
        return getUserProblem(user, problem);
    }

    public UserProblem getUserProblem(User user, Problem problem) {
        Status status = getProblemStatus(problem, user);
        return new UserProblem(problem, status);
    }

    public boolean authenticate(HttpServletRequest request, Integer problemId) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Problem problem = getProblem(problemId);
        return userService.hasAccess(username, problem);
    }

    private Status getProblemStatus(Problem problem, User user) {
        List<Submit> submits = user.getSubmits().stream()
                .filter(submit -> submit.getProblem().equals(problem)).toList();
        if (submits.isEmpty()) {
            return Status.TODO;
        }
        for (Submit s : submits) {
            if (s.getStatus().equals(com.example.learninghub.submit.Status.OK)) {
                return Status.SOLVED;
            }
        }
        return Status.ATTEMPTED;
    }
}
