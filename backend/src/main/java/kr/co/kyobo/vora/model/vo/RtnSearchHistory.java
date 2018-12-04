package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.SearchHistory;
import lombok.Data;

import java.util.List;
@Data
public class RtnSearchHistory {
    private List<PopularSearchKey> popularSearchList;
    private List<SearchHistory> searchHistoryList;
}
