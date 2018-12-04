package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Account extends BaseObject{
    private int userIdx;
    private String accountType;
    private String email;
    private String userPwd;
    private String snsToken;
    private String deviceType;
    private String pushToken;
    private String pushYn;
}
