package io.bissal.spring.elastic.test.component;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

@Component
public class MemSearchRequestBuilder {
    public SearchRequest searchRequest(String hostId) {
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

        return request;
    }
}
