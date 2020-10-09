package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.ProjectRfp;
import io.bissal.spring.elastic.test.model.entity.ProjectRfpItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRfpItemRepository extends JpaRepository<ProjectRfpItem, Long> {
    public ProjectRfpItem findByRegId(String regId);
}
