package kr.co.kyobo.vora.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReturnTags {
    private int page;
    private int limit;
    private int totalCount;
    List<SignUpTags> tags;


}
