package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.service.myFeed.MyFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFeedController {

    @Autowired
    private MyFeedService myFeedService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MY_FEED + UriMethod.GET_MY_FEED)
    public ResponseEntity<Object> getMyFeeds(@RequestBody FindFeedsListObj feeds){
        return myFeedService.getMyFeedsList(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MY_FEED + UriMethod.GET_MY_SCRAPS)
    public ResponseEntity<Object> getMyScraps(@RequestBody FindFeedsListObj feeds){
        return myFeedService.getMyScrapList(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MY_FEED + UriMethod.GET_TOGETHER)
    public ResponseEntity<Object> getTogethers(@RequestBody FindFeedsListObj feeds){
        return myFeedService.getAtMemberList(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MY_FEED + UriMethod.SET_FEED_LIKE_TOGGLE)
    public ResponseEntity<Object> setFeedLikeToggle(@RequestBody Feeds feeds){
        return myFeedService.setFeedLikeToggle(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MY_FEED + UriMethod.SET_FEED_SCRAP_TOGGLE)
    public ResponseEntity<Object> setFeedScrapToggle(@RequestBody Feeds feeds){
        return myFeedService.setFeedScrapToggle(feeds);
    }
}
