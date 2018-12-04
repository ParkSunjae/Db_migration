package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_user_follow")
public class SnsUserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    @Column(columnDefinition = "NVARCHAR")
    private String fuid;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
