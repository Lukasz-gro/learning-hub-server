package com.example.learninghub.judge;

import com.example.learninghub.submit.Status;
import com.example.learninghub.submit.Submit;
import com.example.learninghub.submit.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("queue-code")
    public ResponseEntity<Submit> queueCode(@RequestBody JudgeParams judgeParams) {
        Integer newSubmitId = submitService.addSubmit(judgeParams.getCode(), Status.QUE, judgeParams.getProblemId(), 1);
        judgeQueue.enqueue(judgeParams, newSubmitId);
        return ResponseEntity.ok(submitService.getSubmit(newSubmitId));
    }
}
