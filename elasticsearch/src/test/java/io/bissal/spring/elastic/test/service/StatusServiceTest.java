package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.model.elastic.server.Server;
import io.bissal.spring.elastic.test.model.elastic.server.ServerDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StatusServiceTest {
    @Autowired
    StatusService service;

    @Test
    public void testStatus() {
        // given
        String hostId = "b2211d793fb547419243109dc1f3c0af";

        // when
        ServerDetail status = service.serverDetail(hostId);

        // then
        System.out.println(status);
    }

    @Test
    public void testList() {
        // when
        List<Server> list = service.list();

        // then
        for (Server hostId :
                list) {
            System.out.println(hostId);
        }
    }
}
