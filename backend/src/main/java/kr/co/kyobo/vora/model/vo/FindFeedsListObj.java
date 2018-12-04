package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Feeds;
import lombok.Data;

@Data
public class FindFeedsListObj extends Feeds {
    private int page = 1;
    private int offset = 0;
    private int limit = 10;
    private int totalPage = 0;
    private int parentIdx;
    private String query;
}
