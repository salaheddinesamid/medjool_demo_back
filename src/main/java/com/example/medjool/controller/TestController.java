package com.example.medjool.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security-test")
public class TestController {

    @GetMapping("")
    public String test() {
        return "Security test passed!";
    }
}
