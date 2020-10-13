package io.bissal.spring.elastic.test.dao;

import io.bissal.spring.elastic.test.component.ElasticRestClient;
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
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProcessListDao {
    public static final String TERM_NAME_PROCESSES = "each-processes";

    @Autowired
    private ElasticRestClient client;

    public List<String> server(String hostId) {
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

    private List<String> searchTerms(SearchSourceBuilder search, String termNameServers) {
        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(search);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        Terms terms = response.getAggregations().get(termNameServers);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<String> list = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            list.add(bucket.getKeyAsString());
        }
        return list;
    }
}
