package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.TagRelationFeedRequest;
import kr.co.kyobo.vora.service.mainFeed.MainFeedService;
import kr.co.kyobo.vora.service.tagFeed.TagFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagFeedController {
    @Autowired
    private TagFeedService tagFeedService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAG_FEED + UriMethod.GET_TAG_FEED)
    public ResponseEntity<Object> getTagFeed(@RequestBody TagRelationFeedRequest tagRelationFeedRequest){
        return tagFeedService.getTagFeed(tagRelationFeedRequest);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAG_FEED + UriMethod.GET_TAG_FEED_POPULAR)
    public ResponseEntity<Object> getTagFeedPopular(@RequestBody TagRelationFeedRequest tagRelationFeedRequest){
        return tagFeedService.getTagFeedPopular(tagRelationFeedRequest);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAG_FEED + UriMethod.SET_FEED_LIKE_TOGGLE)
    public ResponseEntity<Object> setFeedLikeToggle(@RequestBody Feeds feeds){
        return tagFeedService.setFeedLikeToggle(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAG_FEED + UriMethod.SET_FEED_SCRAP_TOGGLE)
    public ResponseEntity<Object> setFeedScrapToggle(@RequestBody Feeds feeds){
        return tagFeedService.setFeedScrapToggle(feeds);
    }
}
