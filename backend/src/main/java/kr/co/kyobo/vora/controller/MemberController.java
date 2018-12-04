package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import kr.co.kyobo.vora.model.jwt.LoginToken;
import kr.co.kyobo.vora.model.vo.MemberModifyObj;
import kr.co.kyobo.vora.model.vo.SignUpRequest;
import kr.co.kyobo.vora.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * Member email login
     * @param account
     * @param 
     * @return
     */
    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.SIGN_IN)
    public ResponseEntity<Object> signIn(@RequestBody Account account, HttpServletRequest request) throws IOException {
        return this.memberService.signIn(account, request);
    }

    /**
     * Member With Token 최초 앱 초기화시 실행
     *
     * @param loginToken
     * @param 
     * @return
     */
    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.SIGN_IN_T)
    public ResponseEntity<Object> signInWithToken(@RequestBody LoginToken loginToken,  HttpServletRequest request) throws IOException {
        return this.memberService.signInWithToken(loginToken,  request);
    }

    /**
     * 소셜 로그인 처리
     */
    //TODO 소셜 로그인 처리


    /**
     * Sign up 회원가입
     *
     * @param signUpRequest
     * @param locale
     * @return ReturnObject
     */
    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.SIGN_UP)
    public ResponseEntity<Object> saveMember(@RequestBody SignUpRequest signUpRequest, Locale locale, HttpServletRequest request) throws IOException, GeneralSecurityException, EncoderException {
        return this.memberService.saveMember(signUpRequest, locale, request);
    }
    /**
     * Member Logout 처리
     *
     * @param loginToken
     * @param 
     * @return ReturnObject
     */
    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.SIGN_OUT)
    public ResponseEntity<Object> signOut(@RequestBody LoginToken loginToken,  HttpServletRequest request) throws IOException {
        return this.memberService.signOut(loginToken,  request);
    }


    /**
     * Member nickname 중복 체크
     *
     * @param member
     * @param 
     * @return ResponseEntity<ReturnObject>
     */
    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.CHECK_NICKNAME)
    public ResponseEntity<Object> checkNickName(@RequestBody Member member ) {
        return this.memberService.checkNickName(member);
    }

    /**
     * Member 정보 로드
     * idx only
     *
     * @param member
     * @param 
     * @return
     */
    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.LOAD_MEMBERPROFILE)
    public ResponseEntity<Object> loadMemberProfile(@RequestBody Member member, HttpServletRequest request ) throws IOException {
        return this.memberService.loadMemberProfile(member, request);
    }

    /**
     * Member 정보 수정
     * 회원 프로필 수정 & 비지니스 정보 수정
     *
     * @param member
     * @param profileImage
     * @param defaultImage
     * @param 
     * @return
     */
    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.MODIFY_MEMBERPROFILE, consumes = {"multipart/form-data"})
    public ResponseEntity<Object> modifyMemberProfile(@ModelAttribute Member member,
                                                      @RequestParam(value = "profileImage", required = false)MultipartFile profileImage,
                                                      @RequestParam(value = "defaultImage", required = false) Boolean defaultImage) {
        MemberModifyObj memberModifyObj = new MemberModifyObj();
        BeanUtils.copyProperties(member, memberModifyObj);
        memberModifyObj.setDefaultImage(defaultImage);
        memberModifyObj.setUploadedFile(profileImage);
        return this.memberService.modifyMemberProfile(memberModifyObj);
    }

    /** Member 정보 수정
     * 회원 프로필 수정 & 비지니스 정보 수정
     *
     * @param member
     * @param 
     * @return
     */
    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.SAVE_MEMBER_YEAR)
    public ResponseEntity<Object> saveMemberYear(@RequestBody Member member) {
        return this.memberService.saveMemberYear(member);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.GET_ADMIN_CHECK_USERS)
    public ResponseEntity<Object> getAdminCheckUsers() {
        return this.memberService.getAdminCheckUsers();
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.GET_SUGGEST_USERS)
    public ResponseEntity<Object> getSuggestUsers(@RequestBody List<Integer> users) {
        return this.memberService.getASuggestUsers(users);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.SEND_CERT_MAIL)
    public ResponseEntity<Object> sendMailCert(@RequestBody Member member) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        return this.memberService.sendCertMailToUser(member);
    }

    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.TEMP_PWD_SEND)
    public ResponseEntity<Object> sendTempPwd(@RequestBody Account member, HttpServletRequest request) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        return this.memberService.sendTempPwd(member, request);
    }

    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.CHECK_EMAIL)
    public ResponseEntity<Object> checkMail(@RequestBody Account member) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        return this.memberService.checkUserEmail(member);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER + UriMethod.MEMBER_EXIT)
    public ResponseEntity<Object> memberExit(@RequestBody Member member){
        return this.memberService.memberExit(member);
    }

    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MEMBER + UriMethod.CHECK_DUPLICATE_EMAIL)
    public ResponseEntity<Object> checkDuplicateEmail(@RequestBody Account member){
        return this.memberService.checkDuplicateEmail(member);
    }
}
