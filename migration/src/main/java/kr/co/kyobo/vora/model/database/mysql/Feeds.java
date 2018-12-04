package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.model.database.mssql.SnsFeed;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@Slf4j
public class Feeds extends BaseObject{
    private Integer uIdx;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String contents;
    private Integer hits;
    private Integer likes;
    private Integer scrap;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String link1;
    private String link2;
    private String locationText;

    @OneToMany
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FeedTags> feedTagsList;

    @OneToMany
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FeedMemberTag> feedMemberTagList;

    @OneToMany
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ActionTags> actionTagsList;

    @OneToMany
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FeedFiles> feedFilesList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FeedLiker> feedLikerList;

    @OneToMany
    @JoinColumn(name="feedIdx", referencedColumnName = "idx")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MemberScrapFeeds> memberScrapFeedsList;

    @Transient
    private SnsFeed snsFeedOrigin;

    @Transient
    public static Feeds migration(SnsFeed snsFeed,List<Account> accountList){
        Feeds feeds = new Feeds();
        try{
            Account account = accountList.stream().filter(a -> a.getEmail().equals(snsFeed.getGuid().getStrId()))
                    .findFirst().orElseThrow(NullPointerException::new);
            feeds.setUIdx(account.getUserIdx());
        }catch(NullPointerException e){
            log.info("Feeds : disable to find Member : "+ snsFeed.getGuid().getStrId());
        }
        feeds.setContents(snsFeed.getContents());
        feeds.setHits(snsFeed.getHits());
        feeds.setLikes(snsFeed.getLikes());
        feeds.setScrap(snsFeed.getScrap());
        feeds.setIdx(snsFeed.getIdx().intValue());
        feeds.setLink1(snsFeed.getTagLink());
        feeds.setLink2(snsFeed.getLinkUrl());
        feeds.setCreateAt(CommonUtils.changeDateFormat(snsFeed.getRegdate()));
        feeds.setSnsFeedOrigin(snsFeed);
        return feeds;
    }

    @Transient
    public void migrationSecondery(List<Account> accountList, List<Tags> tagsList){
        SnsFeed snsFeed = this.getSnsFeedOrigin();
        this.setActionTagsList(ActionTags.migrationList(snsFeed.getSnsFeedActionList(), this.getIdx()));
        this.setFeedLikerList(FeedLiker.migrationList(snsFeed.getSnsFeedLikeList(), accountList, this.getIdx()));
        this.setFeedTagsList(FeedTags.migrationList(snsFeed.getSnsFeedTagList(), tagsList, this.getIdx()));
        this.setFeedMemberTagList(FeedMemberTag.migrationList(snsFeed.getSnsFeedAuserList(), accountList, this.getIdx()));
        this.setMemberScrapFeedsList(MemberScrapFeeds.migrationList(snsFeed.getSnsFeedScrapList(), accountList, this.getIdx()));
        this.setFeedFilesList(FeedFiles.migrationList(snsFeed.getSnsFeedFileList(), this.getIdx()));
        if(snsFeed.getStrFile() != null && !snsFeed.getStrFile().equals("")){
            FeedFiles feedFiles = new FeedFiles();
            feedFiles.setFeedIdx(this.getIdx());
            feedFiles.setFileIdx(CommonUtils.getObjectFromString(snsFeed.getStrFile(),"feed"));
            this.getFeedFilesList().add(feedFiles);
        }
    }

    @Transient
    public static List<Feeds> migrationList(List<SnsFeed> snsFeeds, List<Account> accountList){
        List<Feeds> actionTagsList = new ArrayList<>();
        for (SnsFeed snsFeed : snsFeeds) {
            Feeds feedLiker = Feeds.migration(snsFeed, accountList);
            actionTagsList.add(feedLiker);
        }
        return actionTagsList;
    }
}
