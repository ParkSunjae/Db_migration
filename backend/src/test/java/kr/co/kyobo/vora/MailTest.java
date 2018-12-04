package kr.co.kyobo.vora;

import kr.co.kyobo.vora.config.DatabaseConfig;
import kr.co.kyobo.vora.model.api.MailDto;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.repository.MemberRepository;
import kr.co.kyobo.vora.service.mail.SendMailService;
import kr.co.kyobo.vora.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.EncoderException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "classpath:application.properties")
//@Import(DatabaseConfig.class)
public class MailTest {

//    @Autowired
//    private SendMailService sendMailService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void mailTest() throws GeneralSecurityException, UnsupportedEncodingException, EncoderException {
//        Member member = this.memberRepository.findByIdx(2);
//        MailDto mailDto = new MailDto();
//        List<String> to = new ArrayList<>();
//        to.add("ostrich9707@avansoft.co.kr");
//
//        mailDto.setTo(to);
//        mailDto.setFrom("avan_aws_dev@avansoft.co.kr");
//        mailDto.setSubject(this.sendMailService.titleVerify());
//        mailDto.setHtmlBody(this.sendMailService.contentVerification(member));
//        mailDto.setContent("??????");
//
//        this.sendMailService.send(mailDto);
//    }
}
