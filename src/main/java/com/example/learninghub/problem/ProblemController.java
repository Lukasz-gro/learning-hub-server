package com.example.learninghub.problem;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {

    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/v1/auth/problem/{problemId}")
    public Problem getProblemAuthorized(@PathVariable("problemId") Integer problemId) {
        return problemService.getProblem(problemId);
    }

    @GetMapping("/v1/problem/{problemId}")
    public UserProblem getProblem(@PathVariable("problemId") Integer problemId, HttpServletRequest request) {
        if (problemService.authenticate(request, problemId)) {
            return problemService.getUserProblem(request, problemId);
        }
        return null;
    }
}
