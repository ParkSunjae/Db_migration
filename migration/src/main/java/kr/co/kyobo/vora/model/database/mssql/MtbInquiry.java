package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mTb_Inquiry")
public class MtbInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    private String strTitle;
    private String strUserId;
    private String strStatus;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strContent;
    private String strInsertDate;
    private String strAttach;
    private String strCategory;
    @Column(name="intgoods_code")
    private Integer intgoodsCode;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strReContent;
    private String strUpdateDate;
    private String strReName;
    private String strReTitle;
    private String strEmail;
    private String strAttachAdmin;
    private Integer intHit;
    private String strPwd;
    private String strName;
    @Column(columnDefinition = "BIT")
    private Boolean strGubun;
}
