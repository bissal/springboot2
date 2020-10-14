package io.bissal.spring.elastic.test.model.config.search;

import lombok.Data;

@Data
public class Range {
    private String field;
    private String gte;
    private String lte;
}
