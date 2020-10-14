package io.bissal.spring.elastic.test.component;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemSearchRequestBuilder {
    @Value("${search.index}")
    private String searchIndex;

    @Value("${search.field.id}")
    private String id;

    @Value("${search.field.mem}")
    private String mem;

    @Value("${search.include.mem}")
    private List<String> includeMem;

    @Value("${search.range.field}")
    private String rangeField;

    @Value("${search.range.gte}")
    private String gte;

    @Value("${search.range.lte}")
    private String lte;

    @Value("${search.sort}")
    private String sortField;

    public SearchRequest searchRequest(String hostId) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(id, hostId);
        ExistsQueryBuilder existsQuery = QueryBuilders.existsQuery(mem);

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeField)
                .gte(gte)
                .lte(lte)
                ;

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(matchQuery)
                .must(existsQuery)
                .must(rangeQueryBuilder)
                ;

        String[] includeFields = includeMem.toArray(new String[includeMem.size()]);
        String[] excludeFields = new String[] {};

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(includeFields, excludeFields);
        sourceBuilder.query(boolQuery);
        sourceBuilder.size(1);
        sourceBuilder.sort(sortField, SortOrder.DESC);

        SearchRequest request = new SearchRequest(searchIndex);
        request.source(sourceBuilder);

        return request;
    }
}
