package com.journal.journal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Healthcontroller {
    @GetMapping("/health-check")
    public String health() {
        return "OK";
    }
}
