package com.example.learninghub.submit;

import com.example.learninghub.problem.Problem;
import com.example.learninghub.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SubmitService {

    private final SubmitRepository submitRepository;
    private final ProblemService problemService;
    private final AtomicInteger maxSubmitId = new AtomicInteger();

    @Autowired
    public SubmitService(SubmitRepository submitRepository, ProblemService problemService) {
        this.submitRepository = submitRepository;
        this.problemService = problemService;
        maxSubmitId.set(0);
        submitRepository.findTopByOrderById().ifPresent(submit -> maxSubmitId.set(submit.getId()));
    }

    public Integer addSubmit(String code, String status, Integer problemId) {
        Integer submitId = maxSubmitId.incrementAndGet();
        Problem problem = problemService.getProblem(problemId);
        submitRepository.save(new Submit(submitId, code, status, problem));
        return submitId;
    }

    public void updateSubmit(Integer submitId, String newStatus) {
        submitRepository.updateSubmitStatus(submitId, newStatus);
    }
}
