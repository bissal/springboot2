package io.bissal.spring.elastic.test.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EsExampleServiceTest {
    @Autowired
    EsExampleService service;

    @Test
    public void test() {
        // given
        String hostId = "b2211d793fb547419243109dc1f3c0af";

        // when
        SearchResponse response = service.eachStatus(hostId);

        // then
        System.out.println(response);
        System.out.println(response.status());

        SearchHits hits = response.getHits();
        for (SearchHit hit: hits
                ) {
            Map<String, DocumentField> fields = hit.getFields();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            System.out.println(fields);
            System.out.println(highlightFields);
            System.out.println(sourceAsMap);
        }

        System.out.println();

        Map<String, ProfileShardResult> profileResults = response.getProfileResults();
        System.out.println(profileResults.toString());

        Aggregations aggregations = response.getAggregations();
//        Aggregation aggregation = aggregations.get("each-processes");
//        System.out.println(aggregation.getName());
//        System.out.println(aggregation.getType());

        Map<String, Aggregation> asMap = aggregations.getAsMap();
        System.out.println(asMap);

        for (Map.Entry<String, Aggregation> entry: asMap.entrySet()
             ) {
            System.out.println(entry);

            Aggregation aggregation = entry.getValue();
            System.out.println(aggregation.getName());
            System.out.println(aggregation.getType());
            System.out.println(aggregation.getMetaData());

            Terms terms = (Terms) aggregation;
            System.out.println(terms);

            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            System.out.println(buckets);
            for (Terms.Bucket bucket :
                    buckets) {
                System.out.println(bucket.getKeyAsString());
            }
        }
    }
}
