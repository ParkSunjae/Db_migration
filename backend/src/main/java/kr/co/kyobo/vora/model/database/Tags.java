package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Tags extends BaseObject {
    private String tag;
    private int hits;
    private String useYn;
    private String adminCheckYn;

}
