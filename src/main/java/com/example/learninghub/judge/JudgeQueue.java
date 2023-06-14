package com.example.learninghub.judge;

import com.example.learninghub.submit.Status;
import com.example.learninghub.submit.SubmitService;
import com.example.learninghub.test.TestService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Data
@Service
public class JudgeQueue {
    private final SubmitService submitService;
    private final TestService testService;
    private final JudgeService judgeService;
    private final LinkedBlockingQueue<String> linkedBlockingQueue;

    @Autowired
    JudgeQueue(SubmitService submitService, TestService testService, JudgeService judgeService) {
        this.submitService = submitService;
        this.testService = testService;
        this.judgeService = judgeService;
        linkedBlockingQueue = new LinkedBlockingQueue<>(2);
    }


    public void enqueueSubmit(JudgeParams judgeParams, Integer submitID) {
        new Thread(() -> {enqueue(judgeParams, submitID);}).start();
    }

    private void enqueue(JudgeParams judgeParams, Integer submitID) {
        int tests = testService.getTests(judgeParams.getProblemId()).size();
        final Status[] statuses = new Status[tests];
        final String[] messages = new String[tests];
        Thread[] threads = new Thread[tests];

        for (int i = 0; i < tests; i++) {
            final int testCase = i;
            threads[i] = new Thread(() -> {runTestCase(judgeParams, submitID, testCase, statuses, messages);});
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int idRTE = indexOfStatus(statuses, Status.RTE);
        int idANS = indexOfStatus(statuses, Status.ANS);
        int idTLE = indexOfStatus(statuses, Status.TLE);

        if (idRTE != -1) {
            submitService.updateErrorMessage(submitID, messages[idRTE]);
            submitService.updateSubmit(submitID, Status.RTE);
        } else if (idANS != -1) {
            submitService.updateOutput(submitID, messages[idANS]);
            submitService.updateSubmit(submitID, Status.ANS);
        } else if (idTLE != -1) {
            submitService.updateSubmit(submitID, Status.TLE);
        } else {
            submitService.updateSubmit(submitID, Status.OK);
        }
    }

    private int indexOfStatus(Status[] statuses, Status status) {
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals(status)) {
                return i ;
            }
        }
        return -1;
    }

    private void runTestCase(JudgeParams judgeParams, Integer submitID, Integer testCase, Status[] statuses, String[] messages) {
        try {
            linkedBlockingQueue.put(submitID.toString() + "_" + testCase.toString());
            System.out.println("JudgeQueue: " + submitID.toString() + "_" + testCase.toString() + " added");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String input = testService.getTests(judgeParams.getProblemId()).get(testCase).getInput();
        String output = testService.getTests(judgeParams.getProblemId()).get(testCase).getOutput();
        final String[] userOutput = {null};

        Thread t1 = new Thread(() -> {
            userOutput[0] = judgeService.runCode(judgeParams, submitID, input, testCase);
        });

        try {
            t1.start();
            t1.join(15000);
            System.out.println("Thread joined");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String userOutputVal = userOutput[0];

        if (t1.isAlive()) {
            t1.interrupt();
            judgeService.stopContainer();
            System.out.println("TLE");
            statuses[testCase] = Status.TLE;
        } else {
            String[] outputArr = output.split("\\s+");
            String[] userOutputArr = userOutputVal.split("\\s+");
            StringBuilder sb = new StringBuilder();

            if (userOutputArr.length > 0 && userOutputArr[0].equals("RTE?*#.")) {
                messages[testCase] = userOutputVal.substring(8);
                statuses[testCase] = Status.RTE;
            } else {
                for (String s : userOutputArr) {
                    sb.append(s);
                    sb.append(" ");
                }
                messages[testCase] = sb.toString();
                statuses[testCase] = Arrays.equals(outputArr, userOutputArr) ? Status.OK : Status.ANS;
            }
        }
        if (linkedBlockingQueue.remove(submitID.toString() + "_" + testCase.toString())) {
            System.out.println("JudgeQueue: " + submitID.toString() + "_" + testCase.toString() + " removed");
        }
    }
}
