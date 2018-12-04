package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class CommentMember extends BaseObject{
    private int uIdx;
    private int commentIdx;
}
