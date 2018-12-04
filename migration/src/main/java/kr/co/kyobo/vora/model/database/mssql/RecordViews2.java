package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record_views2")
public class RecordViews2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intSeq")
    private Integer intSeq;
    @Column(columnDefinition = "NVARCHAR")
    private String work1;
    @Column(name = "before_right",columnDefinition = "NVARCHAR")
    private String beforeRight;
    @Column(name = "change_right",columnDefinition = "NVARCHAR")
    private String changeRight;
    @Column(name = "member_id",columnDefinition = "NVARCHAR")
    private String memberId;
    @Column(name = "manage_id",columnDefinition = "NVARCHAR")
    private String manageId;
    @Column(columnDefinition = "NVARCHAR")
    private String ip;
    private String inputdate;
}
