package com.example.learninghub.judge;

import com.example.learninghub.submit.SubmitService;
import com.example.learninghub.test.TestService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Data
@Service
public class JudgeQueue {
    private final JudgeService judgeService;
    private final SubmitService submitService;
    private final TestService testService;

    @Autowired
    JudgeQueue(JudgeService judgeService, SubmitService submitService, TestService testService) {
        this.judgeService = judgeService;
        this.submitService = submitService;
        this.testService = testService;
    }


    public void enqueue(JudgeParams judgeParams, Integer submitID) {
        new Thread(() -> {
            String input = testService.getTests(judgeParams.getProblemId()).get(judgeParams.getTestCase() - 1).getInput();
            String output = testService.getTests(judgeParams.getProblemId()).get(judgeParams.getTestCase() - 1).getOutput();

            String userOutput = judgeService.runCode(judgeParams, submitID, input);

            String [] outputArr = output.split("\\s+");
            String [] userOutputArr = userOutput.split("\\s+");
            submitService.updateSubmit(submitID, Arrays.equals(outputArr, userOutputArr) ? "OK" : "ANS");
        }).start();
    }
}
