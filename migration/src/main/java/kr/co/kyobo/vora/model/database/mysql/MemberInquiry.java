package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table
public class MemberInquiry extends BaseObject{
    private Integer uIdx;
    private String type;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    private String reEmail;
    private String status;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String reContent;
}
