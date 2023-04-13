package com.example.learninghub;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String get() {
        System.out.println("Default call running");
        return "I'm server!";
    }

    @CrossOrigin(origins = "https://learning-hub-ui.vercel.app")
    @GetMapping("/v1/health-check")
    public String health() {
        System.out.println("Health-check running");
        return "Healthy";
    }
}
