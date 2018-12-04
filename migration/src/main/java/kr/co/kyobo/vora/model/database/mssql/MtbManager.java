package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "mTb_Manager")
public class MtbManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    @Column(columnDefinition = "NVARCHAR")
    private String strGubun;
    @Column(columnDefinition = "NVARCHAR")
    private String strName;
    @Column(columnDefinition = "NVARCHAR")
    private String strId;
    @Column(columnDefinition = "NVARCHAR")
    private String strPwd;
    @Column(columnDefinition = "NVARCHAR")
    private String strPhone;
    @Column(columnDefinition = "NVARCHAR")
    private String strMobile;
    @Column(columnDefinition = "NVARCHAR")
    private String strEmail;
    @Column(columnDefinition = "NVARCHAR")
    private String strStaff;
    @Column(columnDefinition = "NVARCHAR")
    private String strDepart;
    @Column(columnDefinition = "NVARCHAR")
    private String strPosition;
    @Column(columnDefinition = "NVARCHAR")
    private String strStaffRank;
    private Integer intLevel;
    private Integer intAction;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String strMemo;
    @Column(name="permit_member",columnDefinition = "NVARCHAR")
    private String permitMember;
    @Column(name="permit_board",columnDefinition = "NVARCHAR")
    private String permitBoard;
    @Column(name="permit_contents",columnDefinition = "NVARCHAR")
    private String permitContents;
    @Column(name="permit_send",columnDefinition = "NVARCHAR")
    private String permitSend;
    @Column(name="permit_default",columnDefinition = "NVARCHAR")
    private String permitDefault;
    @Column(name="permit_manage",columnDefinition = "NVARCHAR")
    private String permitManage;
    @Column(name="permit_statistic",columnDefinition = "NVARCHAR")
    private String permitStatistic;
    @Column(name="permit_config",columnDefinition = "NVARCHAR")
    private String permitConfig;
    @Column(columnDefinition = "NVARCHAR")
    private String passwdChange;
    @Column(columnDefinition = "NVARCHAR")
    private String loginDate;
    private Date dtmInsertDate;
}
