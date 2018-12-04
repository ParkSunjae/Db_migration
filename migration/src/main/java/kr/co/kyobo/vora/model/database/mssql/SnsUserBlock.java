package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_user_block")
public class SnsUserBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    @Column(columnDefinition = "NVARCHAR")
    private String buid;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
