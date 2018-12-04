package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.service.mainFeed.MainFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainFeedController {
    @Autowired
    private MainFeedService mainFeedService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MAIN_FEED + UriMethod.GET_MAIN_FEED)
    public ResponseEntity<Object> getMyFeeds(@RequestBody FindFeedsListObj feeds){
        return mainFeedService.getMainFeedsList(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MAIN_FEED + UriMethod.SET_FEED_LIKE_TOGGLE)
    public ResponseEntity<Object> setFeedLikeToggle(@RequestBody Feeds feeds){
        return mainFeedService.setFeedLikeToggle(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MAIN_FEED + UriMethod.SET_FEED_SCRAP_TOGGLE)
    public ResponseEntity<Object> setFeedScrapToggle(@RequestBody Feeds feeds){
        return mainFeedService.setFeedScrapToggle(feeds);
    }
}
