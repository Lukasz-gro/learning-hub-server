package com.example.learninghub.judge;

import com.example.learninghub.submit.Submit;
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
    public Submit runCode(@RequestBody JudgeParams judgeParams) throws InterruptedException {
        Integer newSubmitId = submitService.addSubmit(judgeParams.getCode(), "QUE", judgeParams.getProblemId());
//        Integer newSubmitId = 1;
        judgeQueue.enqueue(judgeParams, newSubmitId, "Filip");
        System.out.println(newSubmitId);
        return submitService.getSubmit(newSubmitId);
    }
}
