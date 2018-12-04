package kr.co.kyobo.vora.service.mail;

import kr.co.kyobo.vora.model.api.MailDto;
import kr.co.kyobo.vora.model.database.Member;
import org.apache.commons.codec.EncoderException;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public interface SendMailService {
    Boolean send(MailDto mailDto);

    String titleVerify();
    String titlePassword();
    String titleDeActive();
    String titleInAvtice();

    String contentVerification(Member member) throws GeneralSecurityException, UnsupportedEncodingException, EncoderException;
    String changePwdMail(Member member, String password);
    String deActiveAccountMail(Member member);
    String inActiveAccountMail(Member member);



}
