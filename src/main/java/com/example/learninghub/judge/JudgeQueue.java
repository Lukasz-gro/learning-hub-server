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
            final String[] userOutput = {null};

            Thread t1 = new Thread(() -> {
                userOutput[0] = judgeService.runCode(judgeParams, submitID, input);
            });

            try {
                t1.start();
                t1.join(15000);
                System.out.println("Thread joined");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (linkedBlockingQueue.peek().equals(submitID)) {
                linkedBlockingQueue.poll();
            }

            String userOutputVal = userOutput[0];

            if (t1.isAlive()) {
                t1.interrupt();
                judgeService.stopContainer();
                System.out.println("TLE");
                submitService.updateSubmit(submitID, Status.TLE);
            } else {
                String[] outputArr = output.split("\\s+");
                String[] userOutputArr = userOutputVal.split("\\s+");
                StringBuffer sb = new StringBuffer();

                if (userOutputArr.length > 0 && userOutputArr[0].equals("RTE?*#.")) {
                    submitService.updateErrorMessage(submitID, userOutputVal.substring(8));
                    submitService.updateSubmit(submitID, Status.RTE);
                } else {
                    for(int i = 0; i < userOutputArr.length; i++) {
                        sb.append(userOutputArr[i]);
                        sb.append(" ");
                    }

                    submitService.updateOutput(submitID, sb.toString());
                    submitService.updateSubmit(submitID, Arrays.equals(outputArr, userOutputArr) ? Status.OK : Status.ANS);
                }
            }
        }).start();
    }
}
