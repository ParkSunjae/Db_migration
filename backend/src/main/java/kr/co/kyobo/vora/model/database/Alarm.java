package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Alarm extends BaseObject{
    private int uIdx;
    private int targetIdx;
    private String type;
    private String message;
    private String showYn;
    private int feedIdx;
}
