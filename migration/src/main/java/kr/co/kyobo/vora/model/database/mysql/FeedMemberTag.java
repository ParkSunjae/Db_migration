package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedAuser;
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
public class FeedMemberTag extends BaseObject {
    private Integer feedIdx;
    private Integer uIdx;

    @Transient
    public static List<FeedMemberTag> migrationList(List<SnsFeedAuser> snsFeedLikeList, List<Account> accountList, Integer feedIdx){
        List<FeedMemberTag> actionTagsList = new ArrayList<>();
        for (SnsFeedAuser snsFeedAuser : snsFeedLikeList) {
            FeedMemberTag feedTags = new FeedMemberTag();
            feedTags.setFeedIdx(feedIdx);

            try{
                Account account = accountList.stream().filter(a -> a.getEmail().equals(snsFeedAuser.getUid().getStrId()))
                        .findFirst().orElseThrow(NullPointerException::new);
                feedTags.setUIdx(account.getUserIdx());
                actionTagsList.add(feedTags);
            }catch(NullPointerException e){
                log.info("FeedMemberTag : disable to find Member : "+ snsFeedAuser.getUid().getStrId());
            }
        }
        return actionTagsList;
    }
}
