package com.example.learninghub.test;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemRepository;
import com.example.learninghub.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final ProblemRepository problemRepository;

    public List<Test> getTests(Integer problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        return testRepository.findTestsByProblem(problem);
    }

}
