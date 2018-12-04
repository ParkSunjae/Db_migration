package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_scrap")
public class SnsFeedScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
