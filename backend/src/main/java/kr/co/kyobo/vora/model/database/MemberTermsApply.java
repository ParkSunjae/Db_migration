package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberTermsApply extends BaseObject{
    private int uIdx;
    private int termIdx;
    private String checkYn;
}
