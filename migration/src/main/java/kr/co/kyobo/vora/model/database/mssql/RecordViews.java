package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record_views")
public class RecordViews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    @Column(columnDefinition = "NVARCHAR")
    private String menu;
    @Column(name = "manage_id", columnDefinition = "NVARCHAR")
    private String manageId;
    @Column(name = "manage_name", columnDefinition = "NVARCHAR")
    private String manageName;
    @Column(columnDefinition = "NVARCHAR")
    private String ip;
    private String inputdate;
}
