package kr.co.kyobo.vora.service.memberFeed;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

public interface MemberFeedService {
    ResponseEntity<Object> getMemberFeed(FindFeedsListObj feeds);

    ResponseEntity<Object> getMemberFeedTogether(FindFeedsListObj feeds);

    ResponseEntity<Object> setFeedLikeToggle(Feeds feeds);

    ResponseEntity<Object> setFeedScrapToggle(Feeds feeds);
}
