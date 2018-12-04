package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Notice extends BaseObject {
    private String title;
    private String content;
    private String useYn;
}
