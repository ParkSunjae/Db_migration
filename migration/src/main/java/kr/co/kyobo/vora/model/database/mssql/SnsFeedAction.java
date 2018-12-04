package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_action")
public class SnsFeedAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String guid;
    @Column(columnDefinition = "NVARCHAR")
    private String atype;
    @Column(columnDefinition = "NVARCHAR")
    private String kyoboId;
    @Column(columnDefinition = "NVARCHAR")
    private String title;
    @Column(columnDefinition = "NVARCHAR")
    private String subTitle;
    @Column(name="url_image",columnDefinition = "NVARCHAR")
    private String urlImage;
    @Column(name="url_image_mobile",columnDefinition = "NVARCHAR")
    private String urlImageMobile;
    @Column(name="url_link",columnDefinition = "NVARCHAR")
    private String urlLink;
    @Column(name="url_link_mobile",columnDefinition = "NVARCHAR")
    private String urlLinkMobile;
    @Column(columnDefinition = "NVARCHAR")
    private String scategory;
    @Column(columnDefinition = "NVARCHAR")
    private String location;
    @Column(columnDefinition = "NVARCHAR")
    private String isbn;
    @Column(columnDefinition = "NVARCHAR")
    private String actor;
    @Column(columnDefinition = "NVARCHAR")
    private String director;
    @Column(columnDefinition = "NVARCHAR")
    private String startDate;
    @Column(columnDefinition = "NVARCHAR")
    private String endDate;
    @Column(columnDefinition = "NVARCHAR")
    private String pubDate;
    @Column(columnDefinition = "NVARCHAR")
    private String useRating;
    @Column(name="temp_delete",columnDefinition = "NCHAR(1)")
    private String tempDelete;
}
