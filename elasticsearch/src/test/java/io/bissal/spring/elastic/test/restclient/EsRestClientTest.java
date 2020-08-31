package io.bissal.spring.elastic.test.restclient;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EsRestClientTest {

    @Autowired
    RestHighLevelClient client;

    @Test
    public void testHighLevelClient() throws IOException {
        SearchRequest request = new SearchRequest("metricbeat-*").types("info");
        SearchResponse sr = client.search(request, RequestOptions.DEFAULT);
        System.out.println(sr.toString());
    }

}
