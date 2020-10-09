package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
