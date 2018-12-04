package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_report")
public class SnsFeedReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String type;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String report;
    private String ip;
    @Column(name="report_check")
    private String reportCheck;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String contents;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
    @Lob
    @Column(name="admin_contents",columnDefinition = "NTEXT")
    private String adminContents;
}
