package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Notice extends BaseObject {
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private String useYn;
}
