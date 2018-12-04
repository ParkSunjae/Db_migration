package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table
public class FeedComment extends BaseObject{
    private Integer parentIdx;
    private Integer feedIdx;
    private Integer uIdx;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String commentContents;
    private Integer likes;

}
