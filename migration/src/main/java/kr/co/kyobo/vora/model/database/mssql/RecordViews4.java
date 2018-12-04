package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record_views4")
public class RecordViews4 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intSeq")
    private Integer intSeq;
    @Column(name = "law1_yn",columnDefinition = "CHAR(1)")
    private String law1Yn;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String law1;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String law2;
    @Column(name = "law3_yn",columnDefinition = "CHAR(1)")
    private String law3Yn;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String law3;
    @Column(name = "law4_yn",columnDefinition = "CHAR(1)")
    private String law4Yn;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String law4;
    @Column(name = "law5_yn",columnDefinition = "CHAR(1)")
    private String law5Yn;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String law5;
    @Column(name = "law6_yn",columnDefinition = "CHAR(1)")
    private String law6Yn;
}
