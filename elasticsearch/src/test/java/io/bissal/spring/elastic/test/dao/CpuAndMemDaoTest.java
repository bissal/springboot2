package io.bissal.spring.elastic.test.dao;

import io.bissal.spring.elastic.test.component.ElasticRestClient;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CpuAndMemDaoTest {
    @Autowired
    ElasticRestClient client;

    @Autowired
    CpuAndMemDao dao;

    @Test
    public void testStat() {
        // given
        String hostId = "b2211d793fb547419243109dc1f3c0af";

        // when
        MultiSearchResponse response = dao.stat(hostId);

        // then
        System.out.println(response);
    }
}
