package com.learninghub.learninghub.judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/judge")
public class JudgeController {

    private final JudgeService judgeService;

    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @GetMapping
    public String get() {
        return "I'm judge!";
    }

    @PostMapping("run-code")
    public void runCode(@RequestBody JudgeParams judgeParams) {
        judgeService.runCode(judgeParams);
    }

}
