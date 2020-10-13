package io.bissal.spring.elastic.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import org.junit.jupiter.api.Test;

public class FooTest {
    String json = "{\"system\":{\"cpu\":{\"total\":{\"pct\":0.0297,\"norm\":{\"pct\":0.0297}}}}}";

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Object o = mapper.readValue(json, Object.class);
        System.out.println(o);
    }

    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        System.out.println(jsonNode);
        JsonNode pct = jsonNode.findValue("pct");
        System.out.println(pct);

        JsonNode cpu = jsonNode.findPath("cpu");
        System.out.println(cpu);

        Cpu cpu1 = mapper.treeToValue(cpu, Cpu.class);
        System.out.println(cpu1);
        System.out.println(cpu1.getTotal());
        System.out.println(cpu1.getTotal().getNorm());
    }
}
