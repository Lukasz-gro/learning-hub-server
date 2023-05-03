package com.example.learninghub.judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/judge")
public class JudgeController {

    private final JudgeQueue judgeQueue;

    @Autowired
    public JudgeController(JudgeQueue judgeQueue) {
        this.judgeQueue = judgeQueue;
    }

    @GetMapping
    public String get() {
        return "I'm judge!";
    }

    @PostMapping("run-code")
    @ResponseBody
    public String runCode(@RequestBody JudgeParams judgeParams) throws InterruptedException {
        System.out.println("runCode1");
        Integer newSubmitId = 0; // TODO: Get new unique submit ID
        return judgeQueue.enqueue(judgeParams, newSubmitId);
//        return Integer.toString(newSubmitId);
    }

}
