package kr.co.kyobo.vora.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class FinalCommentObj {
    List<FeedCommentParent> rtns;
    private int page;
    private int totalCount;
    private int limit;
}
