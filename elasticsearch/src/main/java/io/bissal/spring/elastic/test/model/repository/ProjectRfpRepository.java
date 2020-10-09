package io.bissal.spring.elastic.test.model.repository;

import io.bissal.spring.elastic.test.model.entity.ProjectRfp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRfpRepository extends JpaRepository<ProjectRfp, Long> {
    public ProjectRfp findByRegId(String regId);
}
