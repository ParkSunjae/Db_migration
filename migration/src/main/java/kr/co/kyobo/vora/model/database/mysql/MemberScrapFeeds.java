package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedScrap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@Slf4j
public class MemberScrapFeeds extends BaseObject {
    private Integer feedIdx;
    private Integer uIdx;

    @Transient
    public static List<MemberScrapFeeds> migrationList(List<SnsFeedScrap> snsFeedLikeList, List<Account> accountList, Integer feedIdx){
        List<MemberScrapFeeds> memberScrapFeedsList = new ArrayList<>();
        for (SnsFeedScrap snsFeedScrap : snsFeedLikeList) {
            MemberScrapFeeds memberScrapFeeds = new MemberScrapFeeds();
            memberScrapFeeds.setFeedIdx(feedIdx);

            try{
                Account account = accountList.stream().filter(a -> a.getEmail().equals(snsFeedScrap.getUid()))
                        .findFirst().orElseThrow(NullPointerException::new);
                memberScrapFeeds.setUIdx(account.getUserIdx());
                memberScrapFeedsList.add(memberScrapFeeds);
            }catch(NullPointerException e){
                log.info("MemberScrapFeeds : disable to find Member : "+ snsFeedScrap.getUid());
            }
        }
        return memberScrapFeedsList;
    }
}
