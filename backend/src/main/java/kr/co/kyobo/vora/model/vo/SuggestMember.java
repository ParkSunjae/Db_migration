package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Files;
import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;

@Data
public class SuggestMember extends Member {
    private String categoryNameParent;
    private String categoryNameChild;
    private int followerCount;
    private Boolean followBool = false;
    private Files profileInfo;
}
