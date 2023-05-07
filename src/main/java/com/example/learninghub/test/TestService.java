package com.example.learninghub.test;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TestService {

    private final ProblemService problemService;
    private final TestRepository testRepository;

    @Autowired
    public TestService(ProblemService problemService, TestRepository testRepository) {
        this.problemService = problemService;
        this.testRepository = testRepository;
    }

    public List<Test> getTests(Integer problemId) throws NoSuchElementException {
        Problem problem;
        try {
            problem = problemService.getProblem(problemId);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No problem with id: " + problemId);
        }
        return testRepository.findTestsByProblem(problem);
    }

}
