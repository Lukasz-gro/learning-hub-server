package com.example.learninghub.submit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/v1/submit")
public class SubmitController {

    private final SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping({"{submitId}"})
    public ResponseEntity<Submit> getSubmit(@PathVariable("submitId") Integer id) {
        try {
            Submit submit = submitService.getSubmit(id);
            return ResponseEntity.ok(submit);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
