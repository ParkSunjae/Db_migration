package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedReport extends BaseObject{
    private int feedsIdx;
    private int reportUidx;
    private String type;
    private String report;
    private String comment;
    private String reportCheck;
    private String adminContents;
}
