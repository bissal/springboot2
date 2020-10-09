package io.bissal.spring.elastic.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "prjt_rfp_item")
public class ProjectRfpItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prjt_rfp_item_no") private Long id;
    @Column(name = "prjt_recrut_no") private Long prjtRecrutNo;
    @Column(name = "grp_no") private Integer grpNo;
    @Column(name = "rfp_item_cd") private String rfpItemCd;
    @Column(name = "rfp_item_dtl_cd") private String rfpItemDtlCd;
    @Column(name = "itm_val") private String itmVal;
    @Column(name = "use_yn") private String useYn;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_dt") private LocalDateTime regDt;
    @Column(name = "reg_ip") private String regIp;
    @Column(name = "upd_id") private String updId;
    @Column(name = "upd_dt") private LocalDateTime updDt;
    @Column(name = "upd_ip") private String updIp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ProjectRfp projectRfp;
}
