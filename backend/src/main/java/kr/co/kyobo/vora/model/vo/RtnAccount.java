package kr.co.kyobo.vora.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RtnAccount {
    private int userIdx;
    private String accountType;
    private String email;
    private String userPwd;
    private String snsToken;
    private String deviceType;
    private String pushToken;
    private String pushYn;
    private Date accountCreateAt;

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

    private Boolean facebook = false;
    private Boolean google = false;
    private Boolean naver = false;
    private Boolean kakao = false;
}
