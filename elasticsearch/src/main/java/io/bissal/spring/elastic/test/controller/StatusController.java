package io.bissal.spring.elastic.test.controller;

import io.bissal.spring.elastic.test.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/detail")
    public ResponseEntity<String> processes(@RequestParam String serverHostId) {
        List<String> statusList =  statusService.status(serverHostId);
        String response = statusList.toString();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<String> list() {
        List<String> list = statusService.list();
        String response = list.toString();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
