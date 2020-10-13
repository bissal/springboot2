package io.bissal.spring.elastic.test.dao;

import io.bissal.spring.elastic.test.component.ElasticRestClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemDao {
    @Autowired
    private ElasticRestClient client;

    public SearchResponse stat(String hostId) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("host.id", hostId);
        ExistsQueryBuilder existsQuery = QueryBuilders.existsQuery("system.memory");

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp")
                .gte("now-10h/h")
                .lte("now")
                ;

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(matchQuery)
                .must(existsQuery)
                .must(rangeQueryBuilder)
                ;

        String[] includeFields = new String[] {"system.memory.total","system.memory.used","system.memory.free"};
        String[] excludeFields = new String[] {};

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(includeFields, excludeFields);
        sourceBuilder.query(boolQuery);
        sourceBuilder.size(1);
        sourceBuilder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        return response;
    }
}
