package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedComment extends BaseObject{
    private int parentIdx;
    private int feedIdx;
    private int uIdx;
    private String commentContents;
    private int likes;

}
