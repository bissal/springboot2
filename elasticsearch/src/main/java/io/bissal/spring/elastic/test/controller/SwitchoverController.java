package io.bissal.spring.elastic.test.controller;

import io.bissal.spring.elastic.test.model.entity.Switchover;
import io.bissal.spring.elastic.test.service.SwitchoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/switchover")
public class SwitchoverController {
    @Autowired
    private SwitchoverService service;

    @GetMapping("/status")
    public Switchover status() {
        return service.findLastEntry();
    }

    @PostMapping("/status")
    public String status(@RequestParam String param) {
        service.findLastEntry();
        return null;
    }
}
