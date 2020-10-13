package io.bissal.spring.elastic.test.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bissal.spring.elastic.test.component.ElasticRestClient;
import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import io.bissal.spring.elastic.test.model.elastic.mem.Memory;
import io.bissal.spring.elastic.test.model.elastic.server.Server;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CpuAndMemDaoTest {
    @Autowired
    ElasticRestClient client;

    @Autowired
    CpuAndMemDao dao;

    @Test
    public void testStat() throws JsonProcessingException {
        // given
        String hostId = "b2211d793fb547419243109dc1f3c0af";

        // when
        Server server = dao.stat(hostId);

        // then
        System.out.println(server);
    }
}
