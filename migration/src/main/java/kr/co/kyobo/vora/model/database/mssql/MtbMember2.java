package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "mTb_Member2",schema = "dbo")
public class MtbMember2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    private String strName;
    private String strUid;
    private String strId;
    private String strPwd;
    private String strPhone;
    private String strMobil;
    private String strEmail;
    private String strJob;
    private Date dtmInsertDate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strMemo;
    @Column(columnDefinition = "TINYINT")
    private Integer intGubun;
    @Column(columnDefinition = "TINYINT")
    private Integer intAction;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strActionMessage;
    private String strAgreeId;
    @Column(name="google_token")
    private String googleToken;
    @Column(name="naver_token")
    private String naverToken;
    @Column(name="facebook_token")
    private String facebookToken;
    @Column(name="kakao_token")
    private String kakaoToken;
    @Column(name="strmailok",columnDefinition = "NVARCHAR")
    private String strmailok;
    private String strName2;
    private String strMobil2;
    private String strEmail2;
    @Column(columnDefinition = "NCHAR(1)")
    private String marketting;
    @Column(name="sns_name")
    private String snsName;
    private String strimg;
    @Lob
    @Column(name="contents",columnDefinition = "TEXT")
    private String contents;
    @Column(name="service_confirm",columnDefinition = "NCHAR(1)")
    private String serviceConfirm;
    @Column(name="business_category1")
    private String businessCategory1;
    @Column(name="business_category2")
    private String businessCategory2;
    @Column(name="business_group")
    private String businessGroup;
    @Column(name="business_group_name")
    private String businessGroupName;
    @Column(name="business_sdate")
    private String businessSdate;
    @Column(name="business_edate")
    private String businessEdate;
    @Column(name="business_content")
    private String businessContent;
    @Column(name="business_change_date")
    private String businessChangeDate;
    @Column(name="business_name")
    private String businessName;
    @Column(name="business_email")
    private String businessEmail;
    @Column(name="business_tel")
    private String businessTel;
    @Column(name="business_site")
    private String businessSite;
    @Column(name="business_date")
    private String businessDate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strimg1;
    @Column(name="admin_chun")
    private String adminChun;
    @Column(name="admin_chun_sdate")
    private String adminChunSdate;
    @Column(name="admin_chun_edate")
    private String adminChunEdate;
    @Lob
    @Column(name="admin_chun_content",columnDefinition = "TEXT")
    private String adminChunContent;
    @Column(name="admin_chun_date")
    private String adminChunDate;
    @Column(name="admin_hum_date")
    private String adminHumDate;
    @Column(columnDefinition = "NCHAR")
    private String birthYear;
    @Column(name="push_device",columnDefinition = "NVARCHAR")
    private String pushDevice;
    @Column(name="push_Hp")
    private String pushHp;
    @Column(name="push_Key")
    private String pushKey;
    @Column(name="push_date")
    private String pushDate;
    private String offtel;
    private String offcheck;
    private String offname;
    private String offmanager;
    private String offtitle;
    @Column(columnDefinition = "TINYINT")
    private Integer passwdWrong;
    @Column(columnDefinition = "NVARCHAR")
    private String passwdChange;
    @Column(columnDefinition = "NVARCHAR")
    private String loginDate;
    @Column(columnDefinition = "NVARCHAR")
    private String logoutDate;
    @Column(columnDefinition = "NVARCHAR")
    private String leaveDate;
}
