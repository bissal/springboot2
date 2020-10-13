package io.bissal.spring.elastic.test.controller;

import io.bissal.spring.elastic.test.model.elastic.server.Server;
import io.bissal.spring.elastic.test.model.elastic.server.ServerDetail;
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
    public ResponseEntity<String> serverDetail(@RequestParam String serverHostId) {
        ServerDetail serverDetail =  statusService.serverDetail(serverHostId);
        String response = serverDetail.toString();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Server>> list() {
        List<Server> list = statusService.list();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
