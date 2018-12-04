package kr.co.kyobo.vora.service.tagFeed;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.TagRelationFeedRequest;
import org.springframework.http.ResponseEntity;

public interface TagFeedService {
    ResponseEntity<Object> getTagFeed(TagRelationFeedRequest feeds);

    ResponseEntity<Object> getTagFeedPopular(TagRelationFeedRequest feeds);

    ResponseEntity<Object> setFeedLikeToggle(Feeds feeds);

    ResponseEntity<Object> setFeedScrapToggle(Feeds feeds);
}
