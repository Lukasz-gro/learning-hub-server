package com.example.learninghub.submit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class SubmitController {

    private final SubmitService submitService;

    @Autowired
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @GetMapping("/v1/submit/{submitId}")
    public Submit getSubmit(@PathVariable("submitId") Integer submitId, HttpServletRequest request) {
        if (submitService.authenticate(request, submitId)) {
            return submitService.getSubmit(submitId);
        }
        return null;
    }

    @GetMapping("/v1/submit/{username}/{problemId}/history")
    public List<Submit> getSubmitHistory(@PathVariable("username") String username,
                                         @PathVariable("problemId") Integer problemId,
                                         HttpServletRequest request) {
        if (submitService.authenticate(request, username)) {
            return submitService.getSubmitHistory(username, problemId);
        }
        return null;
    }

}
