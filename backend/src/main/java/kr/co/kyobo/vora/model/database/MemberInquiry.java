package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberInquiry extends BaseObject{
    private String uIdx;
    private String type;
    private String content;
    private String reEmail;
    private String status;
    private String reContent;
}
