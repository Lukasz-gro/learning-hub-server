package com.example.learninghub.judge;

import com.example.learninghub.submit.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/judge")
public class JudgeController {

    private final JudgeQueue judgeQueue;
    private final SubmitService submitService;

    @Autowired
    public JudgeController(JudgeQueue judgeQueue, SubmitService submitService) {
        this.judgeQueue = judgeQueue;
        this.submitService = submitService;
    }

    @GetMapping
    public String get() {
        return "I'm judge!!!";
    }

    @PostMapping("run-code")
    @ResponseBody
    public String runCode(@RequestBody JudgeParams judgeParams) {
        Integer newSubmitId = submitService.addSubmit(judgeParams.getCode(), "QUE", judgeParams.getProblemId());
        judgeQueue.enqueue(judgeParams, newSubmitId);
        return Integer.toString(newSubmitId);
    }
}
