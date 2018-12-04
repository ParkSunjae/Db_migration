package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.SearchHistory;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.service.searchFeed.SearchFeedService;
import kr.co.kyobo.vora.service.searchHistory.SearchHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class SearchHistoryController {
    @Autowired
    SearchHistoryService searchFeedService;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.SEARCH_HISTORY + UriMethod.SEARCH_HISTORY)
    public ResponseEntity<Object> getSearchHistory(@RequestBody Member member){
        return this.searchFeedService.getSearchHistory(member);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.SEARCH_HISTORY + UriMethod.SAVE_SEARCH_HISTORY)
    public ResponseEntity<Object> saveSearchHistory(@RequestBody SearchHistory searchHistory, HttpServletRequest request){
        return this.searchFeedService.saveSearchHistory(searchHistory, request);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.SEARCH_HISTORY + UriMethod.DELETE_SEARCH_HISTORY)
    public ResponseEntity<Object> deleteSearchHistory(@RequestBody SearchHistory searchHistory){
        return this.searchFeedService.deleteSearchHistory(searchHistory);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.SEARCH_HISTORY + UriMethod.DELETE_ALL_SEARCH_HISTORY)
    public ResponseEntity<Object> deleteAllSearchHistory(@RequestBody SearchHistory searchHistory){
        return this.searchFeedService.deleteAllSearchHistory(searchHistory);
    }
}
