package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.Switchover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwitchoverRepository extends JpaRepository<Switchover, Long> {
    Optional<Switchover> findFirstByOrderByIdDesc();
}
