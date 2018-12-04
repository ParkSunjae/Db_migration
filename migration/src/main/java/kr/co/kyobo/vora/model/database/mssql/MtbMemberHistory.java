package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mTb_Member_history")
public class MtbMemberHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    @Column(columnDefinition = "NVARCHAR")
    private String adminId;
    @Column(columnDefinition = "NVARCHAR")
    private String strId;
    @Column(columnDefinition = "NVARCHAR")
    private String strItem;
    @Column(columnDefinition = "NVARCHAR")
    private String strGroup;
    @Column(columnDefinition = "NVARCHAR")
    private String strRecommend;
    @Column(columnDefinition = "NVARCHAR")
    private String strTitle;
    @Column(columnDefinition = "NVARCHAR")
    private String strSdate;
    @Column(columnDefinition = "NVARCHAR")
    private String strEdate;
    @Lob
    @Column(columnDefinition = "NTEXT")
    private String strContent;
    @Column(columnDefinition = "NVARCHAR")
    private String strIP;
    @Column(columnDefinition = "NVARCHAR")
    private String regDate;
}
