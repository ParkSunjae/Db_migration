package kr.co.kyobo.vora.service.searchHistory;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.SearchHistory;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SearchHistoryService {
    ResponseEntity<Object> getSearchHistory(Member member);
    ResponseEntity<Object> saveSearchHistory(SearchHistory searchHistory, HttpServletRequest request);
    ResponseEntity<Object> deleteSearchHistory(SearchHistory searchHistory);
    ResponseEntity<Object> deleteAllSearchHistory(SearchHistory searchHistory);
}
