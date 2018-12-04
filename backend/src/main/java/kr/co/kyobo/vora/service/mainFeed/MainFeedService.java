package kr.co.kyobo.vora.service.mainFeed;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

public interface MainFeedService {
    ResponseEntity<Object> getMainFeedsList(FindFeedsListObj feeds);

    ResponseEntity<Object> setFeedLikeToggle(Feeds feeds);

    ResponseEntity<Object> setFeedScrapToggle(Feeds feeds);
}
