package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Tags;
import lombok.Data;

@Data
public class TagRelationFeedRequest extends Tags {
    private int uIdx;
    private int page = 1;
    private int offset = 0;
    private int limit = 10;
    private int total = 0;
}
