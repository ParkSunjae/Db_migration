package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.repository.FeedCommentRepository;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseMyFeeds {

    private int idx;
    private int uidx;
    private String contents;
    private int hits;
    private int likes;
    private int scrap;
    private String link1;
    private String link2;
    private String feedStatus;
    private String tagLocation;
    private String tagAction;

    private FeedLocations feedLocations;
    private Boolean meLikeCheck;
    private Boolean meCommentCheck;
    private Boolean meScrapCheck;
    private List<ResponseFeedComment> comments;
    private List<FeedCommentParent> commentList;
    private int commentCount;
    private List<Files> files;
    private int fileCount;
    private List<FeedLiker> likers;
    private int likeCount;
    private List<Tags> feedTags;
    private List<FeedMemberTag> atTags;
    private List<ActionTags> actionTags;
    private List<RtnVoraBook> books;
    private List<ActionTagsMovie> movies;
    private List<ActionTagsCultureExhibition> culture;
    private List<MemberScrapFeeds> scraps;
    private int scrapCount;
    private List<Member> feedMemberTag;
    private Member member;
    private Files memberProfile;
    private Account account;

    private Date createAt;
    private Date updateAt;

    //feed 수정 여부
    private Boolean feedMenuShow = false;
    private Boolean feedEdit = false;
    private Boolean feedDelete = false;


}
