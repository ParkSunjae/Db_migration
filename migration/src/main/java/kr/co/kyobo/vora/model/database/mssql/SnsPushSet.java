package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_push_set")
public class SnsPushSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Integer no;
    @Column(columnDefinition = "NVARCHAR")
    private String enc;
    @Column(name="data_all",columnDefinition = "TINYINT")
    private Integer dataAll;
    @Column(name="data_like",columnDefinition = "TINYINT")
    private Integer dataLike;
    @Column(name="data_rep",columnDefinition = "TINYINT")
    private Integer dataRep;
    @Column(name="data_new",columnDefinition = "TINYINT")
    private Integer dataNew;
    @Column(name="data_follow",columnDefinition = "TINYINT")
    private Integer dataFollow;
}
