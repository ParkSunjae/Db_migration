package kr.co.kyobo.vora.service.searchFeed;

import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SearchFeedService {
    ResponseEntity<Object> searchFeedList(FindFeedsListObj findFeedsListObj, HttpServletRequest request);
}
