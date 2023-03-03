package com.learninghub.learninghub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String get() {
        return "I'm server!";
    }

    @GetMapping("/v1/health-check")
    public String health() {
        return "Healthy";
    }
}
