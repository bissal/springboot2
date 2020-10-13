package io.bissal.spring.elastic.test.model.elastic.server;

import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import io.bissal.spring.elastic.test.model.elastic.mem.Memory;
import lombok.Data;

import java.util.List;

@Data
public class ServerDetail {
    private String id;

    private Cpu cpu;
    private Memory memory;

    private List<String> processes;
}
