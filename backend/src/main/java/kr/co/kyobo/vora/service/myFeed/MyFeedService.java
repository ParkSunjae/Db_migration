package kr.co.kyobo.vora.service.myFeed;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

public interface MyFeedService {
    ResponseEntity<Object> getMyFeedsList(FindFeedsListObj feeds);

    ResponseEntity<Object> setFeedLikeToggle(Feeds feeds);

    ResponseEntity<Object> setFeedScrapToggle(Feeds feeds);

    ResponseEntity<Object> getMyScrapList(FindFeedsListObj feeds);

    ResponseEntity<Object> getAtMemberList(FindFeedsListObj feeds);
}
