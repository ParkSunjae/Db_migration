package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class ActionTagsMovie {
    private int idx;
    private int actionTagsIdx;
    private String actor;
    private String director;
    private String mImage;
    private String mLink;
    private String pubDate;
    private String mSubtitle;
    private String mTitle;
    private String userRating;
}
