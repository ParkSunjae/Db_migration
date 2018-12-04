package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_comment")
public class SnsFeedComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
    @Column(columnDefinition = "NVARCHAR")
    private String uname;
    private Long fidx;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String contents;
    private Integer likes;
    @Column(columnDefinition = "NVARCHAR")
    private String regdate;
}
