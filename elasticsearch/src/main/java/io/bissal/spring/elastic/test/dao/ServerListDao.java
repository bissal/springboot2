package io.bissal.spring.elastic.test.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bissal.spring.elastic.test.component.CpuSearchRequestBuilder;
import io.bissal.spring.elastic.test.component.ElasticRestClient;
import io.bissal.spring.elastic.test.component.MemSearchRequestBuilder;
import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import io.bissal.spring.elastic.test.model.elastic.mem.Memory;
import io.bissal.spring.elastic.test.model.elastic.server.Server;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServerListDao {
    @Autowired
    private ElasticRestClient client;


}
