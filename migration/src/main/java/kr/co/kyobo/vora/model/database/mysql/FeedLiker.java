package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedLike;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table
@Slf4j
@IdClass(FeedAndMemberIdx.class)
public class FeedLiker {
    @Id
    private Integer feedIdx;
    @Id
    private Integer uIdx;
    private Date createAt;
    private Date updateAt;

    @Transient
    public static List<FeedLiker> migrationList(List<SnsFeedLike> snsFeedLikeList, List<Account> accountList, Integer feedIdx){
        List<FeedLiker> actionTagsList = new ArrayList<>();
        for (SnsFeedLike snsFeedLike : snsFeedLikeList) {
            try{
                FeedLiker feedLiker = new FeedLiker();
                feedLiker.setFeedIdx(feedIdx);
                Account account = accountList.stream().filter(a -> a.getEmail().equals(snsFeedLike.getUid()))
                        .findFirst().orElseThrow(NullPointerException::new);
                feedLiker.setUIdx(account.getUserIdx());
                actionTagsList.add(feedLiker);
            }catch(NullPointerException e){
                log.info("FeedLiker : disable to find Member : "+ snsFeedLike.getUid());
            }
        }
        return actionTagsList;
    }
}
