package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "sns_feed")
public class SnsFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne
    @JoinColumn(name="guid", referencedColumnName="strUid", columnDefinition = "nvarchar")
    private MtbMember2 guid;
//    @Column(columnDefinition="NVARCHAR")
//    private String guid;
    @Column(columnDefinition="NVARCHAR")
    private String uid;
    @Column(columnDefinition="NVARCHAR")
    private String uname;
    @Lob
    @Column(columnDefinition="NTEXT")
    private String contents;
    private Integer hits;
    private Integer likes;
    private Integer scrap;
    @Column(columnDefinition="NVARCHAR")
    private String strFile;
    @Column(name="file_cnt")
    private Integer fileCnt;
    @Column(name = "link_url",columnDefinition="NVARCHAR")
    private String linkUrl;
    @Column(name = "tag_link",columnDefinition="NVARCHAR")
    private String tagLink;
    @Column(name = "tag_location",columnDefinition="NVARCHAR")
    private String tagLocation;
    @Lob
    @Column(name = "tag_action",columnDefinition="NTEXT")
    private String tagAction;
    @Column(columnDefinition="NVARCHAR")
    private String device;
    @Column(columnDefinition="NVARCHAR")
    private String report;
    @Column(columnDefinition="NVARCHAR")
    private String regdate;
    @Column(columnDefinition="NVARCHAR")
    private String thumb;
    @Column(name="url_title",columnDefinition="NVARCHAR")
    private String urlTitle;
    @Column(columnDefinition="CHAR(1)")
    private String chked;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedAuser> snsFeedAuserList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedTag> snsFeedTagList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedAction> snsFeedActionList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedFile> snsFeedFileList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedLike> snsFeedLikeList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="fidx", referencedColumnName="idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SnsFeedScrap> snsFeedScrapList;
}
