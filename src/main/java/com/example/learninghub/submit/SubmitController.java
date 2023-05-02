package com.example.learninghub.submit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/submit")
public class SubmitController {

    private final SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping
    public String get() {
        return "I'm submit!";
    }
}
