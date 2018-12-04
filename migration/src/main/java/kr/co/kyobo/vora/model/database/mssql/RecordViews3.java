package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record_views3")
public class RecordViews3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intSeq")
    private Integer intSeq;
    @Column(columnDefinition = "NVARCHAR")
    private String startdate;
    @Column(columnDefinition = "NVARCHAR")
    private String enddate;
    @Column(columnDefinition = "NVARCHAR")
    private String state;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String contents;
}
