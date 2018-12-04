package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_location")
public class SnsFeedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String address;
    @Column(columnDefinition = "NVARCHAR")
    private String category;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String description;
    @Column(name="url_link",columnDefinition = "NVARCHAR")
    private String urlLink;
    @Column(columnDefinition = "NVARCHAR")
    private String mapx;
    @Column(columnDefinition = "NVARCHAR")
    private String mapy;
    @Column(columnDefinition = "NVARCHAR")
    private String roadAddress;
    @Column(columnDefinition = "NVARCHAR")
    private String title;
}
