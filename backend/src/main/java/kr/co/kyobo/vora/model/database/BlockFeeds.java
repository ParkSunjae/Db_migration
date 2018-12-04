package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class BlockFeeds extends BaseObject {
    private int feedIdx;
    private int uIdx;
    private String blockStatus;

}
