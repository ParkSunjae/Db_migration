package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Tags;
import lombok.Data;

import java.util.List;

@Data
public class FinalObjTags {
    private Tags tagsInfo;
    List<ResponseMyFeeds> rtns;
    private int page;
    private int totalCount;
    private int limit;
    private int followCount;
}
