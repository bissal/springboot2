package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.model.entity.Switchover;
import io.bissal.spring.elastic.test.model.repository.SwitchoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SwitchoverService {
    @Autowired
    SwitchoverRepository repository;

    public Switchover findLastEntry() {
        Optional<Switchover> optSwitchover = repository.findFirstByOrderByIdDesc();

        return optSwitchover.map(switchover -> {
            return switchover;
        }).orElseThrow();
    }
}
