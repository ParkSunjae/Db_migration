package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_like")
public class SnsFeedLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
