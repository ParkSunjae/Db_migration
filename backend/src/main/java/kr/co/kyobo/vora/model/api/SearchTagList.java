package kr.co.kyobo.vora.model.api;

import kr.co.kyobo.vora.model.database.Tags;
import lombok.Data;

import java.util.List;

@Data
public class SearchTagList extends Tags {
    private int page;
    private int offset;
    private int totalCount;
    private int limit;

    private int uIdx;
    private List<Tags> tags;
}
