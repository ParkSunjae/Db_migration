package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class AdminMemberChangeHistory extends BaseObject{
    private int adminIdx;
    private int uIdx;
    private String comment;
}
