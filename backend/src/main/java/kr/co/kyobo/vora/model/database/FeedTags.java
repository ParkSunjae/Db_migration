package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedTags extends BaseObject{
    private int feedIdx;
    private int tagsIdx;
}
