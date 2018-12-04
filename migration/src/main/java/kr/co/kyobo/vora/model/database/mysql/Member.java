package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import java.util.Date;

@Data
@Entity
@Table
public class Member extends BaseObject{
    private Integer groupIdx;
    private Integer fileIdx;
    private String year;
    private String nickName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private String businessYn;
    private String businessName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String businessContent;
    private Date businessChangeDate;
    private Integer businessCategoryIdx;
    private Integer businessSubCategoryIdx;
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
