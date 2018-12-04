package kr.co.kyobo.vora.service.memberBlockMember;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberBlockMember;
import kr.co.kyobo.vora.model.vo.MemberBlockMemberRequest;
import kr.co.kyobo.vora.model.vo.MemberObj;
import kr.co.kyobo.vora.repository.FilesRepository;
import kr.co.kyobo.vora.repository.MemberBlockMemberRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MemberBlockMemberServiceImpl implements MemberBlockMemberService {
    @Autowired
    MemberBlockMemberRepository memberBlockMemberRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FilesRepository filesRepository;

    @Override
    public ResponseEntity<Object> blockInfo(MemberBlockMemberRequest memberBlockMemberRequest) {
        int total = memberBlockMemberRepository.countByOIdx(memberBlockMemberRequest);
        memberBlockMemberRequest.setTotal(total);
        if(total > 0){
            memberBlockMemberRequest = setPage(memberBlockMemberRequest);

            List<MemberBlockMember> list = memberBlockMemberRepository.findByOIdx(memberBlockMemberRequest);

            List<Integer> memberList = new ArrayList<>();
            for (MemberBlockMember mb: list) {
                memberList.add(mb.getTIdx());
            }
            List<MemberObj> memberList2 = memberRepository.findByIdxMulti2(memberList);
            for (MemberObj mem : memberList2){
                if(mem.getFileIdx()>0){
                    mem.setProfileInfo(filesRepository.findByIdx(mem.getFileIdx()));
                }
            }
            memberBlockMemberRequest.setMemberInfoList(memberList2);
            memberBlockMemberRequest.setMemberBlockMemberList(list);
        }
        return ResponseEntityUtil.makeResultSuccess(memberBlockMemberRequest);
    }

    @Override
    public ResponseEntity<Object> toggleMemberBlockMember(MemberBlockMember memberBlockMember) {
        MemberBlockMember finded = memberBlockMemberRepository.findByOIdxAndTIdx(memberBlockMember);
        if(finded == null)
            memberBlockMemberRepository.insertMemberBlockMember(memberBlockMember);
        else
            memberBlockMemberRepository.deleteByOIdxAndTIdx(memberBlockMember);
        return ResponseEntityUtil.makeResultSuccess("");
    }

    private MemberBlockMemberRequest setPage(MemberBlockMemberRequest memberBlockMemberRequest) {
        if(memberBlockMemberRequest.getPage() > 1){

            if(memberBlockMemberRequest.getTotal() < memberBlockMemberRequest.getPage() * memberBlockMemberRequest.getLimit()){
                memberBlockMemberRequest.setPage(
                        memberBlockMemberRequest.getTotal()/memberBlockMemberRequest.getLimit()
                                + (memberBlockMemberRequest.getTotal()%memberBlockMemberRequest.getLimit()>0?1:0)
                );
            }
            memberBlockMemberRequest.setOffset((memberBlockMemberRequest.getPage() - 1) * memberBlockMemberRequest.getLimit());
        }else{
            memberBlockMemberRequest.setOffset((memberBlockMemberRequest.getPage() - 1));
        }
        return memberBlockMemberRequest;
    }
}