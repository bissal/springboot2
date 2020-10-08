package io.bissal.spring.elastic.test.restclient;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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

    @Test
    public void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("metricbeat-*").searchType(SearchType.DEFAULT);

        SearchResponse sr = client.search(request, RequestOptions.DEFAULT);
        System.out.println(sr.toString());
    }

    @Test
    public void testSearchLast() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        builder.size(1);
        builder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    @Test
    public void testSearchLastById() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();

        QueryBuilder matchQuery = QueryBuilders.matchQuery("process.name", "metricbeat")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);

        builder.query(matchQuery);
        builder.size(1);
        builder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

}
