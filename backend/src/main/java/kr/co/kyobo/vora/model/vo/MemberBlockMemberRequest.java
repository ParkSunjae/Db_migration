package kr.co.kyobo.vora.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberBlockMember;
import lombok.Data;

import java.util.List;

@Data
public class MemberBlockMemberRequest {
    private int originIdx = 0;
    @JsonIgnore
    private int offset = 1;
    private int page = 1;
    private int total = 0;
    private int limit = 10;
    private List<MemberBlockMember> memberBlockMemberList;
    private List<MemberObj> memberInfoList;
}
