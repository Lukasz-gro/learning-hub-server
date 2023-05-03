package com.example.learninghub.judge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.SynchronousQueue;

@Data
@NoArgsConstructor
@Service
public class JudgeQueue {
    @Data
    @AllArgsConstructor
    private static class Submit { // TODO: New class for @Majkelos
        private JudgeParams judgeParams;
        private Integer submitID;
    }

//    private SynchronousQueue<Submit> submitSynchronousQueue = new SynchronousQueue<>();
    private JudgeService judgeService = new JudgeService();

    
    public String enqueue(JudgeParams judgeParams, Integer submitID) throws InterruptedException {
//        System.out.println("enqueue");
//        submitSynchronousQueue.put(new Submit(judgeParams, submitID));
//        System.out.println("put");
        // TODO: EXECUTOR
//        Submit submit = submitSynchronousQueue.poll();
//        System.out.println("poll");
//        if (submit == null) {
//            return null;
//        }

        String input = "some input"; // TODO: get input for specific problem
        String output = judgeService.runCode(judgeParams, submitID, input);
        String correctOutput = "some output\n"; // TODO: Get correct output for particular problem

        // TODO: Change submit status
        System.out.println(output);
        System.out.println(correctOutput);
        return output.equals(correctOutput) ? "OK" : "ANS";
    }
}
