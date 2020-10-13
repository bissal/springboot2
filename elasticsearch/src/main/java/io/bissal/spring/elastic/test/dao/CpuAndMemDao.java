package io.bissal.spring.elastic.test.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bissal.spring.elastic.test.component.CpuSearchRequestBuilder;
import io.bissal.spring.elastic.test.component.ElasticRestClient;
import io.bissal.spring.elastic.test.component.MemSearchRequestBuilder;
import io.bissal.spring.elastic.test.model.elastic.cpu.Cpu;
import io.bissal.spring.elastic.test.model.elastic.mem.Memory;
import io.bissal.spring.elastic.test.model.elastic.server.CpuAndMem;
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
public class CpuAndMemDao {
    @Autowired
    private ElasticRestClient client;

    @Autowired
    private CpuSearchRequestBuilder cpuSearchRequestBuilder;

    @Autowired
    private MemSearchRequestBuilder memSearchRequestBuilder;

    public CpuAndMem stat(String hostId) {
        SearchRequest requestCpu = cpuSearchRequestBuilder.searchRequest(hostId);
        SearchRequest requestMem = memSearchRequestBuilder.searchRequest(hostId);

        MultiSearchRequest multiRequest = new MultiSearchRequest();
        multiRequest.add(requestCpu);
        multiRequest.add(requestMem);

        MultiSearchResponse response = client.multiSearch(multiRequest, RequestOptions.DEFAULT);

        CpuAndMem cpuAndMem = null;
        try {
            cpuAndMem = extract(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return cpuAndMem;
    }

    private CpuAndMem extract(MultiSearchResponse response) throws JsonProcessingException {
        CpuAndMem cpuAndMem = new CpuAndMem();

        MultiSearchResponse.Item[] responses = response.getResponses();
        for (MultiSearchResponse.Item item : responses) {
            SearchHits hits = item.getResponse().getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(sourceAsString);
                if (!jsonNode.findPath("cpu").isEmpty()) {
                    JsonNode cpuNode = jsonNode.findPath("cpu");
                    Cpu cpu = mapper.treeToValue(cpuNode, Cpu.class);
                    cpuAndMem.setCpu(cpu);
                }
                if (!jsonNode.findPath("memory").isEmpty()) {
                    JsonNode memNode = jsonNode.findPath("memory");
                    Memory mem = mapper.treeToValue(memNode, Memory.class);
                    cpuAndMem.setMemory(mem);
                }
            }
        }

        return cpuAndMem;
    }
}
