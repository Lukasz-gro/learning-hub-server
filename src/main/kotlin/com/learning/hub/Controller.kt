package com.learning.hub

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @GetMapping("/")
    fun default() = "I'm test server"

    @GetMapping("/v1/health-check")
    fun get() = "Server is healthy"
}