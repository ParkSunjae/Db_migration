package kr.co.kyobo.vora.service.member;

import org.apache.commons.codec.EncoderException;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public interface MemberService {
    void checkUserStatus() throws UnsupportedEncodingException, GeneralSecurityException, EncoderException;
}
