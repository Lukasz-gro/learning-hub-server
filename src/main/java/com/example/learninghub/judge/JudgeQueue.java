package com.example.learninghub.judge;

import com.example.learninghub.submit.Status;
import com.example.learninghub.submit.SubmitService;
import com.example.learninghub.test.TestService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

@Data
@Service
public class JudgeQueue {
    private final JudgeService judgeService;
    private final SubmitService submitService;
    private final TestService testService;
    private final LinkedBlockingQueue<Integer> linkedBlockingQueue;

    @Autowired
    JudgeQueue(JudgeService judgeService, SubmitService submitService, TestService testService) {
        this.judgeService = judgeService;
        this.submitService = submitService;
        this.testService = testService;
        linkedBlockingQueue = new LinkedBlockingQueue<>(1);
    }


    public void enqueue(JudgeParams judgeParams, Integer submitID) {
        new Thread(() -> {
            try {
                linkedBlockingQueue.put(submitID);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String input = testService.getTests(judgeParams.getProblemId()).get(judgeParams.getTestCase() - 1).getInput();
            String output = testService.getTests(judgeParams.getProblemId()).get(judgeParams.getTestCase() - 1).getOutput();

            Thread t1 = new Thread(() -> {judgeService.runCode(judgeParams, submitID, input);});
            t1.start();
            try {
                t1.join(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread joined");

            if (linkedBlockingQueue.peek().equals(submitID)) {
                linkedBlockingQueue.poll();
            }

            if (t1.isAlive()) {
                t1.interrupt();
                System.out.println("TLE");
                submitService.updateSubmit(submitID, Status.TLE);
            } else {
                String userOutput = judgeService.runCode(judgeParams, submitID, input);

                String[] outputArr = output.split("\\s+");
                String[] userOutputArr = userOutput.split("\\s+");
                if (Objects.equals(userOutputArr[0], "RTE?*#.")) {
                    submitService.updateSubmit(submitID, Status.RTE);
                } else {
                    submitService.updateSubmit(submitID, Arrays.equals(outputArr, userOutputArr) ? Status.OK : Status.ANS);
                }
            }
        }).start();
    }
}
