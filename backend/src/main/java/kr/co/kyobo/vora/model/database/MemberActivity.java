package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberActivity extends BaseObject {
    private int memberIdx;
    private String activityComment;
    private String activityType;
    private String ip;
}
