package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_user_report")
public class SnsUserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    @Column(columnDefinition = "NVARCHAR")
    private String ruid;
    @Column(columnDefinition = "NVARCHAR")
    private String type;
    @Column(columnDefinition = "NVARCHAR")
    private String report;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String contents;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
