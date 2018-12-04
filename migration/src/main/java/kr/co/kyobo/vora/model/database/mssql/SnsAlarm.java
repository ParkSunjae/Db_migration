package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_alarm")
public class SnsAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String actId;
    @Column(columnDefinition = "NVARCHAR")
    private String actName;
    @Column(columnDefinition = "NVARCHAR")
    private String tagetId;
    @Column(columnDefinition = "NVARCHAR")
    private String aType;
    @Column(columnDefinition = "NVARCHAR")
    private String postidx;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
    @Column(columnDefinition="TINYINT")
    private Integer newcount;
    @Column(columnDefinition = "NVARCHAR")
    private String viewdate;
}
