package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class TagFollow extends BaseObject {
    private int tagIdx;
    private int uIdx;
}
