package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.Post;
import io.bissal.spring.elastic.test.model.entity.ProjectRfp;
import io.bissal.spring.elastic.test.model.entity.ProjectRfpItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectRfpRepositoryTest {
    @Autowired
    ProjectRfpRepository repository;

    @BeforeEach
    @Transactional
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
        List<ProjectRfpItem> rfpItems = new ArrayList<>();
        rfpItems.add(rfpItem);

        ProjectRfp projectRfp = ProjectRfp.builder()
                .creterMbrInfoNo(1L)
                .prjtTypeCd("cd")
                .prjtRecrutCnt(1)
                .prjtRecrutSdy("20200901")
                .prjtRecrutEdy("20200930")
                .prjtSdy("20200901")
                .prjtEdy("20200930")
                .prjtBackgd("project background")
                .prjtPurpse("project purpose")
                .prjtObligt("project obligt")
                .prjtMethod("project method")
                .prjtEvaltn("project eval")
                .prjtPpic("ppic")
                .regId("setup")
                .regDt(LocalDateTime.now())
                .regIp("192.168.0.1")
                .updId("setup")
                .updDt(LocalDateTime.now())
                .updIp("192.168.0.1")
                .projectRfpItems(rfpItems)
                .build();

        // when
        ProjectRfp save = repository.save(projectRfp);
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
        List<ProjectRfpItem> rfpItems = new ArrayList<>();
        rfpItems.add(rfpItem);

        ProjectRfp projectRfp = ProjectRfp.builder()
                .creterMbrInfoNo(1L)
                .prjtTypeCd("cd")
                .prjtRecrutCnt(1)
                .prjtRecrutSdy("20200901")
                .prjtRecrutEdy("20200930")
                .prjtSdy("20200901")
                .prjtEdy("20200930")
                .prjtBackgd("project background")
                .prjtPurpse("project purpose")
                .prjtObligt("project obligt")
                .prjtMethod("project method")
                .prjtEvaltn("project eval")
                .prjtPpic("ppic")
                .regId("admin")
                .regDt(LocalDateTime.now())
                .regIp("192.168.0.1")
                .updId("admin")
                .updDt(LocalDateTime.now())
                .updIp("192.168.0.1")
                .projectRfpItems(rfpItems)
                .build();

        // when
        ProjectRfp save = repository.save(projectRfp);
        System.out.println(save);

        List<ProjectRfpItem> projectRfpItems = save.getProjectRfpItems();
        System.out.println("==========================");
        System.out.println(projectRfpItems);
        System.out.println("==========================");

        // then
        Optional<ProjectRfp> optRfp = repository.findById(save.getId());
        Assertions.assertTrue(optRfp.isPresent());

        ProjectRfp selectedRfp = optRfp.get();
        System.out.println(selectedRfp);
        Assertions.assertEquals(projectRfp.getId(), selectedRfp.getId());
    }

    @Test
    public void read() {
        // given
        String regId = "setup";

        // when
        ProjectRfp setup = repository.findByRegId(regId);

        // then
        System.out.println(setup);
        Assertions.assertNotNull(setup);
    }

    @Test
    public void update() {
        // given
        String regId = "setup";
        ProjectRfp setup = repository.findByRegId(regId);
        Long setupId = setup.getId();

        ProjectRfp projectRfp = ProjectRfp.builder()
                .id(setupId)
                .prjtBackgd("update")
                .build();

        // when
        ProjectRfp save = repository.save(projectRfp);

        // then
        System.out.println(save);
    }
}
