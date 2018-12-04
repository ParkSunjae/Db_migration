package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class Terms extends BaseObject{
    private String type;
    private String termsContents;
}
