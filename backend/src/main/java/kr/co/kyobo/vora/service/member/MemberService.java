package kr.co.kyobo.vora.service.member;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import kr.co.kyobo.vora.model.jwt.LoginToken;
import kr.co.kyobo.vora.model.vo.MemberModifyObj;
import kr.co.kyobo.vora.model.vo.SignUpRequest;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Locale;

public interface MemberService {
    ResponseEntity<Object> signIn(Account account, HttpServletRequest request) throws IOException;

    ResponseEntity<Object> signInWithToken(LoginToken loginToken,  HttpServletRequest request) throws IOException;

    ResponseEntity<Object> signOut(LoginToken loginToken, HttpServletRequest request) throws IOException;

    ResponseEntity<Object> saveMember(SignUpRequest signUpRequest, Locale locale, HttpServletRequest request) throws IOException, GeneralSecurityException, EncoderException;

    ResponseEntity<Object> checkNickName(Member member);

    ResponseEntity<Object> loadMemberProfile(Member member, HttpServletRequest request) throws IOException;

    ResponseEntity<Object> modifyMemberProfile(MemberModifyObj member);

    ResponseEntity<Object> saveMemberYear(Member member);

    ResponseEntity<Object> getAdminCheckUsers();

    ResponseEntity<Object> getASuggestUsers(List<Integer> users);

    void changeUserMailCert(String encrypt) throws GeneralSecurityException, UnsupportedEncodingException, DecoderException;

    ResponseEntity<Object> sendCertMailToUser(Member member) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException;

    ResponseEntity<Object> sendTempPwd(Account member, HttpServletRequest request) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException;

    ResponseEntity<Object> checkUserEmail(Account member);

    void changeUserStatusMail(String encrypt) throws DecoderException, UnsupportedEncodingException, GeneralSecurityException;

    ResponseEntity<Object> memberExit(Member member);

    ResponseEntity<Object> checkDuplicateEmail(Account member);
}
