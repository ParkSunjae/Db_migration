package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import kr.co.kyobo.vora.service.memberFollowMember.MemberFollowMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class MemberFollowMemberController {

    @Autowired
    private MemberFollowMemberService memberFollowMemberService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FOLLOW_MEMBER + UriMethod.FIND_FOLLOWERS)
    public ResponseEntity<Object> findFollowerListByNickName(@RequestBody Member member){
        return this.memberFollowMemberService.findTargetFollowerNickName(member);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FOLLOW_MEMBER + UriMethod.TOGGLE_FOLLOW_MEMBER)
    public ResponseEntity<Object> toggleFollowMember(@RequestBody MemberFollowMember memberFollowMember) {
        return this.memberFollowMemberService.toggleFollowMember(memberFollowMember);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FOLLOW_MEMBER + UriMethod.SAVE_MEMBER_FOLLOWER)
    public ResponseEntity<Object> saveFollowers(@RequestBody List<MemberFollowMember> memberFollowMembers) {
        return this.memberFollowMemberService.saveFollowers(memberFollowMembers);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_FOLLOW_MEMBER + UriMethod.CHECK_FOLLOW_MEMBER)
    public ResponseEntity<Object> checkFollowMember(@RequestBody MemberFollowMember memberFollowMember) {
        return this.memberFollowMemberService.checkFollowMember(memberFollowMember);
    }
}
