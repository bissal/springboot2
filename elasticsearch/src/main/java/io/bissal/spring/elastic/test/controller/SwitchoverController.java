package io.bissal.spring.elastic.test.controller;

import io.bissal.spring.elastic.test.service.EsClientService;
import io.bissal.spring.elastic.test.service.SwitchoverService;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/switchover")
public class SwitchoverController {
    @Autowired
    private SwitchoverService service;

    @GetMapping("/status")
    public String status() {
        service.findLastEntry();
        return null;
    }
}
