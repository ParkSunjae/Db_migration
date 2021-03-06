package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class FeedCommentReport extends BaseObject{
    private Integer feedCommentIdx;
    private Integer reportUidx;
    private String type;
    private String report;
    private String comment;
    private String reportCheck;
    private String adminContents;
}
