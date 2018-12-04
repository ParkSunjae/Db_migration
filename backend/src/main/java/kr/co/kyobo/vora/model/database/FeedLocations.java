package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class FeedLocations {
    private int idx;
    private int feedIdx;
    private String address;
    private String category;
    private String description;
    private String link;
    private String mapx;
    private String mapy;
    private String roadAddress;
    private String title;
}
