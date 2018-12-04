package kr.co.kyobo.vora.service.member;

import kr.co.kyobo.vora.model.api.MailDto;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import kr.co.kyobo.vora.repository.AccountRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import kr.co.kyobo.vora.service.history.MemberLoginHistoryService;
import kr.co.kyobo.vora.service.mail.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private static final int MAILDATE = 11;
    private static final int CHANGEDATE = 12;

    @Value("${mail.auth}")
    private String mailAdmin;

    @Autowired
    private MemberLoginHistoryService memberLoginHistory;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SendMailService sendMailService;

    @Override
    public void checkUserStatus() throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        //활성 사용자 목록
        List<MemberLoginHistory> findLoginHistroy = this.memberLoginHistory.findByMemberIdxByLast();

        List<MemberLoginHistory> sendMails = new ArrayList<>();
        List<MemberLoginHistory> changeStatuses = new ArrayList<>();


        for(MemberLoginHistory memberLoginHistory : findLoginHistroy){
            // 오늘을 기준으로 11개월전 (휴면 전 공지 메일)
            if(this.getDaysCountBetweenDates(this.changeTime(memberLoginHistory.getLoginTime()), this.changeTime(memberLoginHistory.getToday())) == MAILDATE){
                sendMails.add(memberLoginHistory);
            }

            // 오늘을 기준으로 12개월전 회원을 휴면 처리한다. (휴면 변경 안내)
            if(this.getDaysCountBetweenDates(this.changeTime(memberLoginHistory.getLoginTime()), this.changeTime(memberLoginHistory.getToday())) == CHANGEDATE){
                changeStatuses.add(memberLoginHistory);
            }
        }

        if(changeStatuses.size() > 0){
            this.memberRepository.changeMemberDeActive(changeStatuses);
            this.sendMail(changeStatuses, false);
        }

        if(sendMails.size() > 0){
            this.sendMail(sendMails, true);
        }
    }

    private void sendMail(List<MemberLoginHistory> list, Boolean checker) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        for(MemberLoginHistory memberLoginHistory : list){
            Account account = this.accountRepository.findByMemberIdx(memberLoginHistory.getUIdx());
            Member member = this.memberRepository.findByMemberIdx(memberLoginHistory.getUIdx());

            log.info("mail==="+account.getEmail());

            MailDto mailDto = new MailDto();
            List<String> to = new ArrayList<>();
            to.add(account.getEmail());

            mailDto.setTo(to);
            mailDto.setFrom(this.mailAdmin);
            if(checker){
                mailDto.setSubject(this.sendMailService.titleDeActive());
                mailDto.setHtmlBody(this.sendMailService.deActiveAccountMail(member));
                mailDto.setContent("VORA의 휴면 전환 전 안내 메일입니다.");
            }else{
                mailDto.setSubject(this.sendMailService.titleInAvtice());
                mailDto.setHtmlBody(this.sendMailService.inActiveAccountMail(member));
                mailDto.setContent("VORA의 휴면 전환 안내 메일입니다.");
            }

            this.sendMailService.send(mailDto);
        }
    }

    public long getDaysCountBetweenDates(LocalDate dateBefore, LocalDate dateAfter) {
//        log.info("비교된 월" + MONTHS.between(dateBefore, dateAfter));
        return MONTHS.between(dateBefore, dateAfter);
    }
    public LocalDate changeTime(String time){
//        log.info("before===="+time);
        String[] split = time.split("\\.");
//        log.info("split[0]===="+split[0]);
        return LocalDate.parse(split[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
