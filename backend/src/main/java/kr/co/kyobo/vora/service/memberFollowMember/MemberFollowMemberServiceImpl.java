package kr.co.kyobo.vora.service.memberFollowMember;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Alarm;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import kr.co.kyobo.vora.repository.AlarmRepository;
import kr.co.kyobo.vora.repository.MemberFollowMemberRepository;
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
public class MemberFollowMemberServiceImpl implements MemberFollowMemberService {

    @Autowired
    private MemberFollowMemberRepository memberFollowMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public ResponseEntity<Object> findTargetFollowerNickName(Member member) {

        List<Member> followerLists = this.memberFollowMemberRepository.findByTargetNickName(member);

        List<Member> finalSuggests = new ArrayList<>();

        if(followerLists.size() > 0){
            finalSuggests = followerLists;
        }else{
            List<Member> allMemberByNickName = this.memberRepository.findByNickName(member);
            finalSuggests = allMemberByNickName;
        }

        return ResponseEntityUtil.makeResultSuccess(finalSuggests);
    }

    @Override
    public ResponseEntity<Object> saveFollowers(List<MemberFollowMember> memberFollowMembers) {
        int rtn = this.memberFollowMemberRepository.saveMulti(memberFollowMembers);

        for (MemberFollowMember get : memberFollowMembers) {

            Member findMember = this.memberRepository.findByIdx(get.getOIdx());
            //TODO push
            Alarm alarm = new Alarm();
            alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님을 팔로우 하였습니다.");
            alarm.setUIdx(get.getTIdx());
            alarm.setShowYn("N");
            alarm.setTargetIdx(get.getOIdx());
            alarm.setType("w");
            this.saveAlarm(alarm);
        }


        if (rtn == 1) {
            return ResponseEntityUtil.makeResultSuccess("");
        } else {
            return ResponseEntityUtil.makeResultError("");
        }
    }

    @Override
    public ResponseEntity<Object> checkFollowMember(MemberFollowMember memberFollowMember) {
        MemberFollowMember memberFollowMemberCheck = this.memberFollowMemberRepository.findByOidxAndTIdx(memberFollowMember);
        if(memberFollowMemberCheck!=null){
            return ResponseEntityUtil.makeResultSuccess(memberFollowMemberCheck);
        }else{
            return ResponseEntityUtil.makeResultSuccess(false);
        }
    }

    @Override
    public ResponseEntity<Object> toggleFollowMember(MemberFollowMember memberFollowMember) {
        MemberFollowMember memberFollowMemberCheck = this.memberFollowMemberRepository.findByOidxAndTIdx(memberFollowMember);
        if(memberFollowMemberCheck==null){
            int rtn = this.memberFollowMemberRepository.save(memberFollowMember);
            Member findMember = this.memberRepository.findByIdx(memberFollowMember.getOIdx());
            //TODO push

            Alarm alarm = new Alarm();
            alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님을 팔로우 하였습니다.");
            alarm.setUIdx(memberFollowMember.getOIdx());
            alarm.setShowYn("N");
            alarm.setTargetIdx(memberFollowMember.getTIdx());
            alarm.setType("w");
            this.saveAlarm(alarm);

            if (rtn == 1) {
                return ResponseEntityUtil.makeResultSuccess("");
            } else {
                return ResponseEntityUtil.makeResultError("");
            }
        }else{
            int rtn = this.memberFollowMemberRepository.deleteMemberFollowMember(memberFollowMemberCheck);
            if (rtn == 1) {
                return ResponseEntityUtil.makeResultSuccess("");
            } else {
                return ResponseEntityUtil.makeResultError("");
            }
        }
    }

    private void saveAlarm(Alarm alarm) {
        this.alarmRepository.save(alarm);
    }
}
