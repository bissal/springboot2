package io.bissal.spring.elastic.test.config;

import io.bissal.spring.elastic.test.model.config.search.Field;
import io.bissal.spring.elastic.test.model.config.search.Range;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search")
@Getter
@Setter
@ToString
public class SearchProperties {
    private String index;
    private Field field;
    private Range range;
    private String sort;
}
