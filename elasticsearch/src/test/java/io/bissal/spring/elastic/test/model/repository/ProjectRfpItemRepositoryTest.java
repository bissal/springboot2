package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.ProjectRfpItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectRfpItemRepositoryTest {
    @Autowired
    ProjectRfpItemRepository repository;

    @BeforeEach
    public void setUp() {
        // given
        ProjectRfpItem rfpItem = ProjectRfpItem.builder()
                .prjtRecrutNo(1L)
                .grpNo(1)
                .rfpItemCd("cd")
                .rfpItemDtlCd("dtlcd")
                .itmVal("val")
                .useYn("Y")
                .regId("setup")
                .regDt(LocalDateTime.now())
                .regIp("192.168.0.1")
                .updId("setup")
                .updDt(LocalDateTime.now())
                .updIp("192.168.0.1")
                .build();

        // when
        ProjectRfpItem save = repository.save(rfpItem);
    }

    @Test
    public void create() {
        // given
        ProjectRfpItem rfpItem = ProjectRfpItem.builder()
                .prjtRecrutNo(1L)
                .grpNo(1)
                .rfpItemCd("cd")
                .rfpItemDtlCd("dtlcd")
                .itmVal("val")
                .useYn("Y")
                .regId("admin")
                .regDt(LocalDateTime.now())
                .regIp("192.168.0.1")
                .updId("admin")
                .updDt(LocalDateTime.now())
                .updIp("192.168.0.1")
                .build();

        // when
        ProjectRfpItem save = repository.save(rfpItem);

        // then
        System.out.println(save);
        Assertions.assertNotNull(save);
    }

    @Test
    public void read() {
        // given
        String regId = "setup";

        // when
        ProjectRfpItem byRegId = repository.findByRegId(regId);

        // then
        System.out.println(byRegId);
        Assertions.assertNotNull(byRegId);
    }
}
