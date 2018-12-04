package kr.co.kyobo.vora.service.memberFollowMember;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberFollowMemberService {
    ResponseEntity<Object> findTargetFollowerNickName(Member member);

    ResponseEntity<Object> toggleFollowMember(MemberFollowMember memberFollowMember);

    ResponseEntity<Object> saveFollowers(List<MemberFollowMember> memberFollowMembers);

    ResponseEntity<Object> checkFollowMember(MemberFollowMember memberFollowMember);
}
