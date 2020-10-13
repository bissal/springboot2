package io.bissal.spring.elastic.test.model.elastic.server;

import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import io.bissal.spring.elastic.test.model.elastic.mem.Memory;
import lombok.Data;

@Data
public class Server {
    private String id;

    private Cpu cpu;
    private Memory memory;
}
