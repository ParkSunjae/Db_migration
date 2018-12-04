package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Help extends BaseObject {
    private String title;
    private String content;
    private String comments;
    private String useYn;
}
