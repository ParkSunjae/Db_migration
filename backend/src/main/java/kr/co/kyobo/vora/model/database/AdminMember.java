package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class AdminMember extends BaseObject{
    private String adminId;
    private String adminPwd;
    private String adminNickName;
    private String adminGrade;
}
