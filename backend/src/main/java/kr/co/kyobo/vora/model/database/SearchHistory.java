package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class SearchHistory extends BaseObject{
    private int uIdx;
    private String search;
    private String ip;
}
