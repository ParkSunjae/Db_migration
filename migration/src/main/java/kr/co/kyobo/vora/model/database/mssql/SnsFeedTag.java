package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_tag")
public class SnsFeedTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;
    private Long fidx;
    @Column(name="tag_name",columnDefinition = "NVARCHAR")
    private String tagName;
    private Integer hits;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    @Column(columnDefinition = "NVARCHAR")
    private String uname;
}
