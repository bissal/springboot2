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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "projectRfpItems")
@Entity
@Table(name = "prjt_rfp")
public class ProjectRfp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prjt_recrut_no") private Long id;
    @Column(name = "creter_mbr_info_no") private Long creterMbrInfoNo;
    @Column(name = "prjt_type_cd") private String prjtTypeCd;
    @Column(name = "prjt_recrut_cnt") private Integer prjtRecrutCnt;
    @Column(name = "prjt_recrut_sdy") private String prjtRecrutSdy;
    @Column(name = "prjt_recrut_edy") private String prjtRecrutEdy;
    @Column(name = "prjt_sdy") private String prjtSdy;
    @Column(name = "prjt_edy") private String prjtEdy;
    @Column(name = "prjt_backgd") private String prjtBackgd;
    @Column(name = "prjt_purpse") private String prjtPurpse;
    @Column(name = "prjt_obligt") private String prjtObligt;
    @Column(name = "prjt_method") private String prjtMethod;
    @Column(name = "prjt_evaltn") private String prjtEvaltn;
    @Column(name = "prjt_ppic") private String prjtPpic;
    @Column(name = "reg_id") private String regId;
    @Column(name = "reg_dt") private LocalDateTime regDt;
    @Column(name = "reg_ip") private String regIp;
    @Column(name = "upd_id") private String updId;
    @Column(name = "upd_dt") private LocalDateTime updDt;
    @Column(name = "upd_ip") private String updIp;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ProjectRfpItem> projectRfpItems;
}
