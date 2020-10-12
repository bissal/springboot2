package io.bissal.spring.elastic.test;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class EsRestClient {
    @Autowired
    RestHighLevelClient client;

    public SearchResponse search(SearchRequest request, RequestOptions requestOptions) {
        SearchResponse response = null;
        try {
            response = client.search(request, requestOptions);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return response;
    }
}
