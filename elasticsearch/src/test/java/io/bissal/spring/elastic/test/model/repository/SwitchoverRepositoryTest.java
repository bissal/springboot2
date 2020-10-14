package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.Switchover;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SwitchoverRepositoryTest {
    @Autowired
    SwitchoverRepository repository;

    @BeforeEach
    public void setUp() {
        // given
        Switchover switchover = Switchover.builder()
                .isAliveMain(Boolean.TRUE)
                .isAliveDr(Boolean.FALSE)
                .createdFrom("main")
                .lastModifiedFrom("main")
                .build();

        // when
        Switchover saved = repository.save(switchover);

        // then
        Assertions.assertEquals(switchover, saved);
    }

    @Test
    public void read() {
        // given
        Long id = 1L;

        // when
        Optional<Switchover> optPost = repository.findById(id);

        // then
        System.out.println(optPost.get());
        Assertions.assertTrue(optPost.isEmpty());
    }

    @Test
    public void findLastEntry() {
        // when
        Optional<Switchover> opt = repository.findFirstByOrderByIdDesc();

        // then
        System.out.println(opt);
    }
}
