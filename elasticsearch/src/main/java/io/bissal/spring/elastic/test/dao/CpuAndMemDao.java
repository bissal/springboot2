package io.bissal.spring.elastic.test.dao;

import io.bissal.spring.elastic.test.component.CpuSearchRequestBuilder;
import io.bissal.spring.elastic.test.component.ElasticRestClient;
import io.bissal.spring.elastic.test.component.MemSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CpuAndMemDao {
    @Autowired
    private ElasticRestClient client;

    @Autowired
    private CpuSearchRequestBuilder cpuSearchRequestBuilder;

    @Autowired
    private MemSearchRequestBuilder memSearchRequestBuilder;

    public MultiSearchResponse stat(String hostId) {
        SearchRequest requestCpu = cpuSearchRequestBuilder.searchRequest(hostId);
        SearchRequest requestMem = memSearchRequestBuilder.searchRequest(hostId);

        MultiSearchRequest multiRequest = new MultiSearchRequest();
        multiRequest.add(requestCpu);
        multiRequest.add(requestMem);

        MultiSearchResponse response = client.multiSearch(multiRequest, RequestOptions.DEFAULT);

        return response;
    }
}
