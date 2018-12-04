package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberLoginHistory extends BaseObject {
    private int uIdx;
    private String ip;
    private String location;
    private String loginTime;
    private String loginType;
    private String logType;
    private String today;
}
