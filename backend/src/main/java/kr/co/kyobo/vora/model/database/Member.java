package kr.co.kyobo.vora.model.database;

import lombok.Data;

import java.util.Date;

@Data
public class Member extends BaseObject{
    private int groupIdx;
    private int fileIdx;
    private String year;
    private String nickName;
    private String content;
    private String businessYn;
    private String businessName;
    private String businessContent;
    private int businessCategoryIdx;
    private int businessSubCategoryIdx;
    private String businessEmail;
    private String businessTel;
    private String businessSite;
    private Date businessSdate;
    private Date businessEdate;
    private String emailCertYn;
    private Date emailCertDate;
    private String userStatus;
    private String adminCheckYn;
}
