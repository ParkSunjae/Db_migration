package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record_permit")
public class RecordPermit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long intSeq;
    @Column(name = "admin_id", columnDefinition = "NVARCHAR")
    private String adminId;
    @Column(name = "manage_id", columnDefinition = "NVARCHAR")
    private String manageId;
    @Column(columnDefinition="TINYINT")
    private Integer intLevel;
    @Column(name = "prePermit_member", columnDefinition = "NVARCHAR")
    private String prePermitMember;
    @Column(name = "prePermit_board", columnDefinition = "NVARCHAR")
    private String prePermitBoard;
    @Column(name = "prePermit_contents", columnDefinition = "NVARCHAR")
    private String prePermitContents;
    @Column(name = "prePermit_send", columnDefinition = "NVARCHAR")
    private String prePermitSend;
    @Column(name = "prePermit_default", columnDefinition = "NVARCHAR")
    private String prePermitDefault;
    @Column(name = "prePermit_manage", columnDefinition = "NVARCHAR")
    private String prePermitManage;
    @Column(name = "prePermit_statistic", columnDefinition = "NVARCHAR")
    private String prePermitStatistic;
    @Column(name = "prePermit_config", columnDefinition = "NVARCHAR")
    private String prePermitConfig;
    @Column(name = "nowPermit_member", columnDefinition = "NVARCHAR")
    private String nowPermitMember;
    @Column(name = "nowPermit_board", columnDefinition = "NVARCHAR")
    private String nowPermitBoard;
    @Column(name = "nowPermit_contents", columnDefinition = "NVARCHAR")
    private String nowPermitContents;
    @Column(name = "nowPermit_send", columnDefinition = "NVARCHAR")
    private String nowPermitSend;
    @Column(name = "nowPermit_default", columnDefinition = "NVARCHAR")
    private String nowPermitDefault;
    @Column(name = "nowPermit_manage", columnDefinition = "NVARCHAR")
    private String nowPermitManage;
    @Column(name = "nowPermit_statistic", columnDefinition = "NVARCHAR")
    private String nowPermitStatistic;
    @Column(name = "nowPermit_config", columnDefinition = "NVARCHAR")
    private String nowPermitConfig;
    @Column(columnDefinition = "NTEXT")
    private String contents;
    @Column(columnDefinition = "NVARCHAR")
    private String ip;
    @Column(columnDefinition = "NVARCHAR")
    private String regDate;
}
