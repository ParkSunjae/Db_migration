package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedLiker extends BaseObject {
    private int feedIdx;
    private int uIdx;

}
