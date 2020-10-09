package io.bissal.spring.elastic.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Switchover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAliveMain;
    private Boolean isAliveDr;

    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    private String createdFrom;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    private String lastModifiedFrom;
}
