package com.example.learninghub.judge;

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
    @ResponseBody
    public String runCode(@RequestBody JudgeParams judgeParams) {
        return judgeService.runCode(judgeParams);
    }

}
