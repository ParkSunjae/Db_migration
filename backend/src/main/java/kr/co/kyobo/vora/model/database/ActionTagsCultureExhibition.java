package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class ActionTagsCultureExhibition {
    private int idx;
    private int actionTagsIdx;
    private int id;
    private String cateName1;
    private String cImage;
    private String cLink;
    private String cLocation;
    private String startDate;
    private String endDate;
    private String cTitle;
}
