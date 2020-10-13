package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.component.ElasticRestClient;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsExampleService {
    public static final String TERM_NAME_PROCESSES = "each-processes";
    public static final String TERM_NAME_SERVERS = "each-server";

    @Autowired
    private ElasticRestClient esRestClient;

    public SearchResponse eachStatus(String hostId) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("host.id", hostId);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(matchQuery);

        TermsAggregationBuilder termsAggregation
                = AggregationBuilders
                .terms(TERM_NAME_PROCESSES)
                .field("system.process.cmdline");

        String[] includeFields = new String[] {"system.process.cmdline"};
        String[] excludeFields = new String[] {};

        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(boolQuery);
        search.aggregation(termsAggregation);
        search.fetchSource(includeFields, excludeFields);
        search.size(0);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(search);

        SearchResponse response = esRestClient.search(request, RequestOptions.DEFAULT);
        return response;
    }

    public List<String> searchCpuMemOfServer(String hostId) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("host.id", hostId);

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp")
                .gte("now-10h/h")
                .lte("now")
                ;

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(matchQuery)
                .must(rangeQueryBuilder);

        String[] includeFields = new String[] {"system.cpu"};
        String[] excludeFields = new String[] {};

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(includeFields, excludeFields);
        sourceBuilder.query(boolQuery);
        sourceBuilder.size(1);
        sourceBuilder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(sourceBuilder);

        MultiSearchRequest multiRequest = new MultiSearchRequest();
        multiRequest.add(request);

        MultiSearchResponse multiSearchResponse = esRestClient.multiSearch(multiRequest, RequestOptions.DEFAULT);
        System.out.println(multiSearchResponse);

        return null;
    }
}
