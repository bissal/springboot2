package io.bissal.spring.elastic.test.restclient;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
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

        String[] includeFields = new String[] {"system.process.cmdline","agent.*"};
        String[] excludeFields = new String[] {"user"};

        builder.fetchSource(includeFields, excludeFields);
        builder.query(matchQuery);
        builder.size(10);
        builder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    @Test
    public void testSearchLastByProcessNames() throws IOException {
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("system.process.cmdline", "*metric*");
        WildcardQueryBuilder wildcardQueryBuilder1 = QueryBuilders.wildcardQuery("system.process.cmdline", "*python*");

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp")
                .gte("now-50h/h")
                .lte("now-1h/h")
                ;

        QueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(wildcardQueryBuilder)
                .should(wildcardQueryBuilder1)
                .should(rangeQueryBuilder)
                ;

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);

        String[] includeFields = new String[] {"system.process","process","host"};
        String[] excludeFields = new String[] {};
        sourceBuilder.fetchSource(includeFields, excludeFields);

        sourceBuilder.size(10);
        sourceBuilder.sort("@timestamp", SortOrder.DESC);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    @Test
    public void testSearchAggsEachProcesses() throws IOException {
        TermsAggregationBuilder termsAggregationBuilder
                = AggregationBuilders
                .terms("each-processes")
                .field("system.process.cmdline");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(termsAggregationBuilder);

        String[] includeFields = new String[] {"system.process.cmdline"};
        String[] excludeFields = new String[] {};
        sourceBuilder.fetchSource(includeFields, excludeFields);

        sourceBuilder.size(0);

        SearchRequest request = new SearchRequest("metricbeat-*");
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }


}
