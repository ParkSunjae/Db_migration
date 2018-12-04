package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedMemberTag extends BaseObject {
    private int feedIdx;
    private int uIdx;
}
