package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table
public class AdminMemberChangeHistory extends BaseObject{
    private Integer adminIdx;
    private Integer uIdx;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String comment;
}
