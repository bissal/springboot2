
package io.bissal.spring.elastic.test.model.elastic.mem;

import lombok.Data;

@Data
public class Memory {
    private String total;
    private String free;
    private Used used;
}
