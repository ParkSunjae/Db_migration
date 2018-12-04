package kr.co.kyobo.vora.service.memberBlockMember;

import kr.co.kyobo.vora.model.database.MemberBlockMember;
import kr.co.kyobo.vora.model.vo.MemberBlockMemberRequest;
import org.springframework.http.ResponseEntity;

public interface MemberBlockMemberService {
    ResponseEntity<Object> blockInfo(MemberBlockMemberRequest memberBlockMemberRequest);
    ResponseEntity<Object> toggleMemberBlockMember(MemberBlockMember memberBlockMemberRequest);
}