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
}
