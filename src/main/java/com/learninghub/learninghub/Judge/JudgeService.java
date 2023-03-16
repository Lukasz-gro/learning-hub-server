package com.learninghub.learninghub.Judge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class JudgeParam {
    String code;
    int problemId;
    boolean baseTestCase;
    //TODO enum
    String language;

    @Override
    public String toString() {
        return code + " " + language;
    }
}

@RestController
public class JudgeService {

    @PostMapping("/v1/judge/run-code")
    public String runCode(@RequestBody String code) {
        System.out.println("Test: " + code);
        return "I'm judge!";
    }
}
