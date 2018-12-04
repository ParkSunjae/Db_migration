package kr.co.kyobo.vora.service.searchHistory;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.*;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SearchHistoryServiceImpl implements SearchHistoryService {
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Override
    public ResponseEntity<Object> getSearchHistory(Member member) {
        List<SearchHistory> historyList = searchHistoryRepository.findByUidx(member.getIdx());
        List<PopularSearchKey> keys = searchHistoryRepository.findPopularSearchKey();
        RtnSearchHistory rtn = new RtnSearchHistory();
        rtn.setPopularSearchList(keys);
        rtn.setSearchHistoryList(historyList);
        return ResponseEntityUtil.makeResultSuccess(rtn);
    }

    @Override
    public ResponseEntity<Object> saveSearchHistory(SearchHistory searchHistory, HttpServletRequest request) {
        searchHistory.setIp(CommonUtils.checkIp(request));
        int rts = searchHistoryRepository.saveQueryHistory(searchHistory);
        if(rts != 0)
            return ResponseEntityUtil.makeResultSuccess("");
        else
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getMessage());
    }

    @Override
    public ResponseEntity<Object> deleteAllSearchHistory(SearchHistory searchHistory) {
        int rts = searchHistoryRepository.deleteAllQueryHistory(searchHistory);
        if(rts != 0)
            return ResponseEntityUtil.makeResultSuccess("");
        else
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getMessage());
    }

    @Override
    public ResponseEntity<Object> deleteSearchHistory(SearchHistory searchHistory) {
        int rts = searchHistoryRepository.deleteQueryHistory(searchHistory);
        if(rts != 0)
            return ResponseEntityUtil.makeResultSuccess("");
        else
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getMessage());
    }
}
