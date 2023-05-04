package com.example.learninghub.judge;

import com.example.learninghub.submit.SubmitService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.SynchronousQueue;

@Data
@Service
public class JudgeQueue {

//    private SynchronousQueue<Submit> submitSynchronousQueue = new SynchronousQueue<>();
    private final JudgeService judgeService;
    private final SubmitService submitService;

    @Autowired
    JudgeQueue(JudgeService judgeService, SubmitService submitService) {
        this.judgeService = judgeService;
        this.submitService = submitService;
    }


    public void enqueue(JudgeParams judgeParams, Integer submitID, String input) throws InterruptedException {
        String output = judgeService.runCode(judgeParams, submitID, input);
        String correctOutput = "Hello, Filip!\n";
        boolean result = output.equals(correctOutput);
        submitService.updateSubmit(submitID, result ? "OK" : "ANS");
    }
}
