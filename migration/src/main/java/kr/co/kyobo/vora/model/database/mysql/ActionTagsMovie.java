package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ActionTagsMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    private Integer actionTagsIdx;
    private String actor;
    private String director;
    private String mImage;
    private String mLink;
    private String pubDate;
    private String mSubtitle;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String mTitle;
    private String userRating;
}
