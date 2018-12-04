package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedTag;
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
public class FeedTags extends BaseObject{
    private Integer feedIdx;
    private Integer tagsIdx;

    @Transient
    public static List<FeedTags> migrationList(List<SnsFeedTag> snsFeedLikeList, List<Tags> tagsList, Integer feedIdx){
        List<FeedTags> actionTagsList = new ArrayList<>();
        for (SnsFeedTag snsFeedTag : snsFeedLikeList) {
            FeedTags feedTags = new FeedTags();
            feedTags.setFeedIdx(feedIdx);

            try{
                Tags tag = tagsList.stream().filter(a -> a.getTag().toLowerCase().equals(snsFeedTag.getTagName().toLowerCase()))
                        .findFirst().orElseThrow(NullPointerException::new);
                feedTags.setTagsIdx(tag.getIdx());
                actionTagsList.add(feedTags);
            }catch(NullPointerException e){
                log.info("FeedTags : disable to find Tag : "+ snsFeedTag.getTagName());
            }
        }
        return actionTagsList;
    }
}
