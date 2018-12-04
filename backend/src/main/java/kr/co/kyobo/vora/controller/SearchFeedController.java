package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.service.searchFeed.SearchFeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class SearchFeedController {
    @Autowired
    SearchFeedService searchFeedService;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.SEARCH_FEED + UriMethod.SEARCH_FEED)
    public ResponseEntity<Object> searchFeedList(@RequestBody FindFeedsListObj findFeedsListObj, HttpServletRequest request){
        return this.searchFeedService.searchFeedList(findFeedsListObj, request);
    }
}
