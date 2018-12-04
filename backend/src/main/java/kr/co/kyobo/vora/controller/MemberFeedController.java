package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.TagRelationFeedRequest;
import kr.co.kyobo.vora.service.memberFeed.MemberFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberFeedController {
    @Autowired
    private MemberFeedService memberFeedService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FEED + UriMethod.GET_MEMBER_FEED)
    public ResponseEntity<Object> getTagFeed(@RequestBody FindFeedsListObj tagRelationFeedRequest){
        return memberFeedService.getMemberFeed(tagRelationFeedRequest);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FEED + UriMethod.GET_MEMBER_FEED_TOGETER)
    public ResponseEntity<Object> getTagFeedPopular(@RequestBody FindFeedsListObj tagRelationFeedRequest){
        return memberFeedService.getMemberFeedTogether(tagRelationFeedRequest);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FEED + UriMethod.SET_FEED_LIKE_TOGGLE)
    public ResponseEntity<Object> setFeedLikeToggle(@RequestBody Feeds feeds){
        return memberFeedService.setFeedLikeToggle(feeds);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FEED + UriMethod.SET_FEED_SCRAP_TOGGLE)
    public ResponseEntity<Object> setFeedScrapToggle(@RequestBody Feeds feeds){
        return memberFeedService.setFeedScrapToggle(feeds);
    }
}
