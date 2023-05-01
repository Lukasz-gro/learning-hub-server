package com.example.learninghub.test;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final ProblemService problemService;
    private final TestRepository testRepository;

    @Autowired
    public TestService(ProblemService problemService, TestRepository testRepository) {
        this.problemService = problemService;
        this.testRepository = testRepository;
    }

    public List<Test> getTests(Integer problemId) {
        Problem problem = problemService.getProblem(problemId);
        return testRepository.findTestsByProblem(problem);
    }

}
