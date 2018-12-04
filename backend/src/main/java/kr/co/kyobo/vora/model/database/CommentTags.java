package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class CommentTags extends BaseObject{
    private int commentIdx;
    private int tagIdx;
}
