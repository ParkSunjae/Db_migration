package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Files;
import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;

@Data
public class MemberObj extends Member{
    Files profileInfo;
}
