package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class BusinessCategory2 extends BaseObject{
    private int parentIdx;
    private String categoryName;
}
