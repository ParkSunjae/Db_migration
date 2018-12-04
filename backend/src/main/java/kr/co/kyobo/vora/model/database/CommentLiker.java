package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class CommentLiker extends BaseObject{
    private int commentIdx;
    private int uIdx;
}
