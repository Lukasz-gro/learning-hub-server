package com.example.learninghub.submit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubmitController {

    private final SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping({"/v1/auth/submit/{submitId}"})
    public Submit getSubmit(@PathVariable("submitId") Integer id) {
        return submitService.getSubmit(id);
    }

}
