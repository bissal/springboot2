package io.bissal.spring.elastic.test.controller;

import io.bissal.spring.elastic.test.service.SwitchoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class SwitchoverController {
    @Autowired
    private SwitchoverService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

//    @GetMapping("/data")
//    public String

    @GetMapping("/status")
    public String status() {
        service.findLastEntry();
    }
}
