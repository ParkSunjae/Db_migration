package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Terms extends BaseObject{
    private String type;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String termsContents;
    private String showYn;
}
