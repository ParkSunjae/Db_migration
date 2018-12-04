package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.MemberBlockMember;
import kr.co.kyobo.vora.model.vo.MemberBlockMemberRequest;
import kr.co.kyobo.vora.service.memberBlockMember.MemberBlockMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberBlockMemberController {
    @Autowired
    private MemberBlockMemberService memberBlockMemberService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_BLOCK_MEMBER + UriMethod.FIND_BLOCKMEMBERS)
    public ResponseEntity<Object> findMemberBlockMemberList(@RequestBody MemberBlockMemberRequest memberBlockMemberRequest){
        return this.memberBlockMemberService.blockInfo(memberBlockMemberRequest);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_BLOCK_MEMBER + UriMethod.TOGGLE_MEMBER_BLOCK_MEMBER)
    public ResponseEntity<Object> toggleMemberBlockMember(@RequestBody MemberBlockMember memberBlockMember){
        return this.memberBlockMemberService.toggleMemberBlockMember(memberBlockMember);
    }
}
