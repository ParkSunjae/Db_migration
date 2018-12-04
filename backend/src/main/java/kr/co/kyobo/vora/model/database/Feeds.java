package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Feeds extends BaseObject{
    private int uIdx;
    private String contents;
    private int hits;
    private int likes;
    private int scrap;
    private String link1;
    private String link2;
    private String feedStatus;
    private String tagLocation;
    private String tagAction;
    private String locationText;
}
