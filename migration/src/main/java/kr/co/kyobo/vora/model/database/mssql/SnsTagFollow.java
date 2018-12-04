package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_tag_follow")
public class SnsTagFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    private Long tidx;
    @Column(columnDefinition = "NVARCHAR")
    private String tag;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
