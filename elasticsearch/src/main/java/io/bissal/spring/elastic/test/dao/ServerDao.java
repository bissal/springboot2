package io.bissal.spring.elastic.test.dao;

import io.bissal.spring.elastic.test.component.ElasticRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServerDao {
    @Autowired
    private ElasticRestClient client;

    
}
