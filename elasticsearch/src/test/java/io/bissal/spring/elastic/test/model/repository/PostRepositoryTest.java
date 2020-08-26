package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostRepository repository;

    @Test
    public void read() {
        // given
        Long id = 0L;

        // when
        Optional<Post> optPost = repository.findById(id);

        // then
        Assertions.assertTrue(optPost.isEmpty());
    }
}
