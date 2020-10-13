package io.bissal.spring.elastic.test.service;

import io.bissal.spring.elastic.test.EsRestClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsClientService {
    public static final String TERM_NAME_PROCESSES = "each-processes";
    public static final String TERM_NAME_SERVERS = "each-server";

    @Autowired
    private EsRestClient esRestClient;

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

    public List<String> status(String hostId) {
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

        List<String> processes = searchTerms(search, TERM_NAME_PROCESSES);

        return processes;
    }

    public List<String> list() {
        List<String> strings = searchEachServers();

        return strings;
    }

    public List<String> searchCpuMemOfServer(String hostId) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("host.id", hostId);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(matchQuery);

        return null;
    }

    public List<String> searchEachServers() {
        TermsAggregationBuilder termsAggregation
                = AggregationBuilders
                .terms(TERM_NAME_SERVERS)
                .field("host.id");

        SearchSourceBuilder search = new SearchSourceBuilder();
        search.aggregation(termsAggregation);
        search.fetchSource(false);
        search.size(0);

        List<String> list = searchTerms(search, TERM_NAME_SERVERS);

        return list;
    }

    private List<String> searchTerms(SearchSourceBuilder search, String termNameServers) {
        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(search);

        SearchResponse response = esRestClient.search(request, RequestOptions.DEFAULT);

        Terms terms = response.getAggregations().get(termNameServers);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<String> list = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            list.add(bucket.getKeyAsString());
        }
        return list;
    }
}
