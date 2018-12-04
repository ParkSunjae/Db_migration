package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_tag")
public class SnsTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String tag;
    private Integer hits;
    @Column(columnDefinition = "NVARCHAR")
    private String ip;
}
