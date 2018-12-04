package kr.co.kyobo.vora.service.member;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.common.encrypt.AES256Util;
import kr.co.kyobo.vora.common.encrypt.SHA256;
import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.messages.ResultConstants;
import kr.co.kyobo.vora.model.api.MailDto;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.jwt.JwtClaim;
import kr.co.kyobo.vora.model.jwt.JwtValidationResult;
import kr.co.kyobo.vora.model.jwt.LoginResponse;
import kr.co.kyobo.vora.model.jwt.LoginToken;
import kr.co.kyobo.vora.model.vo.MemberModifyObj;
import kr.co.kyobo.vora.model.vo.SignUpRequest;
import kr.co.kyobo.vora.model.vo.SuggestMember;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.file.AmazonClient;
import kr.co.kyobo.vora.service.getIp.IpAddressToCityName;
import kr.co.kyobo.vora.service.jwt.JwtService;
import kr.co.kyobo.vora.service.mail.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Value("${userImage.path}")
    private String memberPath;
    @Value("${userImageThm.path}")
    private String memberThumbnailPath;

    @Value("${mail.auth}")
    private String mailAdmin;

    @Value("${postImage.path}")
    private String feedPath;

    @Value("${postImageThm.path}")
    private String feedThumbnailPath;

    @Autowired
    JwtService jwtService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FilesRepository filesRepository;

    @Autowired
    MemberFollowMemberRepository memberFollowMemberRepository;

    @Autowired
    FeedsRepository feedsRepository;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private MemberActivityRepository memberActivityRepository;

    @Autowired
    private MemberLoginHistoryRepository memberLoginHistoryRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private DeleteMemberRepository deleteMemberRepository;

    @Autowired
    private FeedCommentRepository feedCommentRepository;

    @Autowired
    private IpAddressToCityName ipAddressToCityName;

    /**
     * 사용자 로그인
     *
     * @param login
     * @return
     */
    @Override
    public ResponseEntity<Object> signIn(Account login, HttpServletRequest request) throws IOException {
        if (login.getEmail() == null) {
            return ResultConstants.invalidRequest();
        }
        login.setUserPwd(SHA256.getHash(login.getUserPwd()));
        Account user = accountRepository.findByEmailAndPassword(login);
        if (user == null) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_FIND_FAIL.getCode());
        }

        //OS가 들어오는지 체크
        if (login.getDeviceType() == null || !login.getDeviceType().equals("AOS") && !login.getDeviceType().equals("IOS") && !login.getDeviceType().equals("WEB") && !login.getDeviceType().equals("MOBILE(IOS)") && !login.getDeviceType().equals("MOBILE(AOS)") && !login.getDeviceType().equals("MOBILE(ETC)")) {
            return ResultConstants.invalidRequest();
        }

        //deviceToken이 들어오는지 체크
        user.setDeviceType(login.getDeviceType());

        MemberLoginHistory last = this.memberLoginHistoryRepository.findLastHistoryByMemberIdx(user.getUserIdx());

        //deviceToken update
        // 사용자 로그인 히스토리 저장
        MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
        memberLoginHistory.setIp(CommonUtils.checkIp(request));
        memberLoginHistory.setLocation(this.ipAddressToCityName.callCityName(request));
        memberLoginHistory.setUIdx(user.getUserIdx());
        memberLoginHistory.setLoginType(login.getDeviceType());
        memberLoginHistory.setLogType("i");

        memberLoginHistoryRepository.saveLoginHistory(memberLoginHistory);

        String token = jwtService.makeAccessToken(user);
        LoginResponse loginResponse = this.makeResponse(user, token);
        //토큰 서버에 토큰 저장
        //jwtService.setActiveToken(user.getMbrNo(),token);

        loginResponse.setLastLogin(last);

        return ResponseEntityUtil.makeResultSuccess(loginResponse);
    }

    @Override
    public ResponseEntity<Object> signInWithToken(LoginToken loginToken, HttpServletRequest request) throws IOException {
        //parameter 검증
        if (loginToken.getAccessToken() == null || loginToken.getAccessToken().equals("")) {
            log.info("case01");
            return ResultConstants.invalidRequest();
        }

        //클라이언트 토큰 검증
        JwtValidationResult jwtValidationResult = jwtService.validateToken(loginToken.getAccessToken());
        if (!jwtValidationResult.getIsValid()) {
            log.info("case1");
            return ResultConstants.loginFail();
        }

        //토큰서버의 토큰존재 여부 체크
        JwtClaim claim = jwtService.parseToken(loginToken.getAccessToken());
        if (claim == null) {
            log.info("case2");
            return ResultConstants.loginFail();
        }

        //DB정보 확인 및 토큰 갱신;
        Account user = accountRepository.findByAccountIdx(Integer.parseInt(claim.getAidx()));
        if (user == null) {
            log.info("case3");
            return ResultConstants.loginFail();
        }

        Member member = this.memberRepository.findByIdx(Integer.parseInt(claim.getMidx()));

        int finded = this.memberLoginHistoryRepository.todayLoginChecker(member.getIdx());

        if(finded == 0){
            MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
            memberLoginHistory.setIp(CommonUtils.checkIp(request));
            memberLoginHistory.setLocation(this.ipAddressToCityName.callCityName(request));
            memberLoginHistory.setUIdx(user.getUserIdx());
            memberLoginHistory.setLoginType(claim.getDt());
            memberLoginHistory.setLogType("ti");
            memberLoginHistoryRepository.saveLoginHistory(memberLoginHistory);
        }

        //토큰 생성
        String token = jwtService.makeAccessToken(user);
        //액티브 토큰 저장
        //jwtService.setActiveToken(claim.getUno(),token);
//        int rtn = memberRepository.updateMemberLogin(user);
//        log.info("rnt========" + rtn);
        return ResultConstants.checkResult(this.makeResponse(user, token));
    }

    @Override
    public ResponseEntity<Object> signOut(LoginToken loginToken, HttpServletRequest request) throws IOException {
        //parameter 검증
        if (loginToken.getAccessToken() == null || loginToken.getAccessToken().equals("")) {
            log.info("case01");
            return ResultConstants.invalidRequest();
        }

        //클라이언트 토큰 검증
        JwtValidationResult jwtValidationResult = jwtService.validateToken(loginToken.getAccessToken());
        if (!jwtValidationResult.getIsValid()) {
            log.info("case1");
            return ResultConstants.loginFail();
        }

        //토큰서버의 토큰존재 여부 체크
        JwtClaim claim = jwtService.parseToken(loginToken.getAccessToken());
        if (claim == null) {
            log.info("case2");
            return ResultConstants.loginFail();
        }
        log.info(claim.getAidx() + " : "+claim.getAt() + " : "+claim.getDt() + " : "+claim.getMidx());

        Account user = accountRepository.findByAccountIdx(Integer.parseInt(claim.getAidx()));
        if (user == null) {
            log.info("case3");
            return ResultConstants.loginFail();
        }

        MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
        memberLoginHistory.setIp(CommonUtils.checkIp(request));
        memberLoginHistory.setLocation(this.ipAddressToCityName.callCityName(request));
        memberLoginHistory.setUIdx(Integer.parseInt(claim.getMidx()));
        memberLoginHistory.setLoginType(claim.getDt());
        memberLoginHistory.setLogType("o");

        memberLoginHistoryRepository.saveLoginHistory(memberLoginHistory);

        return null;
    }

    @Override
    public ResponseEntity<Object> saveMember(SignUpRequest signUpRequest, Locale locale, HttpServletRequest request) throws IOException, GeneralSecurityException, EncoderException {
        //기회원 존재여부 확인
        //MBR_STS_CD가 1인  MDN이 존재하는 지 여부 체크
        signUpRequest.getAccount().setUserPwd(
                SHA256.getHash(signUpRequest.getAccount().getUserPwd())
        );
        Account account = this.accountRepository.findByEmailAndPassword(signUpRequest.getAccount());
        if (account != null) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.COMMON_JOIN_DUPLICATION_FAIL.getCode());
        }

        int rtn = this.memberRepository.checkNickName(signUpRequest.getMember());
        if (rtn > 0) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.STOLEN_NICKNAME.getCode());
        }

        this.memberRepository.saveMember(signUpRequest.getMember());
        signUpRequest.getAccount().setUserIdx(signUpRequest.getMember().getIdx());
        this.accountRepository.saveAccount(signUpRequest.getAccount());
        String token = jwtService.makeAccessToken(signUpRequest.getAccount());

        //액티브 토큰 저장
        //jwtService.setActiveToken(member.getMbrNo(),token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setDeviceType(signUpRequest.getAccount().getDeviceType());
        loginResponse.setAccountType(signUpRequest.getAccount().getAccountType());
        loginResponse.setMIdx(signUpRequest.getMember().getIdx());
        loginResponse.setAIdx(signUpRequest.getAccount().getIdx());

        this.sendCertMailToUser(signUpRequest.getMember());

        MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
        memberLoginHistory.setIp(CommonUtils.checkIp(request));
        memberLoginHistory.setLocation(this.ipAddressToCityName.callCityName(request));
        memberLoginHistory.setUIdx(signUpRequest.getMember().getIdx());
        memberLoginHistory.setLoginType(signUpRequest.getAccount().getDeviceType());
        memberLoginHistory.setLogType("i");

        memberLoginHistoryRepository.saveLoginHistory(memberLoginHistory);

        //TODO login history save

        return ResultConstants.checkResult(loginResponse);

    }

    private LoginResponse makeResponse(Account account, String token) {
        MemberLoginHistory last = this.memberLoginHistoryRepository.findLastHistoryByMemberIdx(account.getUserIdx());
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setMIdx(account.getUserIdx());
        loginResponse.setAIdx(account.getIdx());
        loginResponse.setDeviceType(account.getDeviceType());
        loginResponse.setAccountType(account.getAccountType());
        loginResponse.setAccessToken(token);
        loginResponse.setLastLogin(last);
        return loginResponse;
    }

    @Override
    public ResponseEntity<Object> checkNickName(Member member) {
        Boolean checker = true;
        int rtn = this.memberRepository.checkNickName(member);

        HashMap<String, Object> result = new HashMap<>();
        if (rtn > 0) {
            checker = false;
        }
        result.put("nickNameCheck", checker);
        return ResponseEntityUtil.makeResultSuccess(result);
    }

    /**
     * 회원 정보 로드시 사용 (idx만 사용)
     * account[accountType==email]인거만 가져옴
     *
     * @param member
     * @return
     */
    @Override
    public ResponseEntity<Object> loadMemberProfile(Member member, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Member member_buff = memberRepository.findByIdx(member.getIdx());
        Account account = accountRepository.findByUserIdx(member.getIdx());
        Files files = filesRepository.findByIdx(member_buff.getFileIdx());
        result.put("followingCount", memberFollowMemberRepository.countByOIdx(member.getIdx()));
        result.put("followerCount", memberFollowMemberRepository.countByTIdx(member.getIdx()));
        result.put("feedCount", feedsRepository.countByUidx(member.getIdx()));
        result.put("member", member_buff);
        result.put("account", account);
        result.put("files", files);
        result.put("locationText", this.ipAddressToCityName.callCityName(request));
        return ResponseEntityUtil.makeResultSuccess(result);
    }


    /**
     * 회원 프로필 수정 & 비지니스 정보 수정
     *
     * @param member
     * @return
     */
    @Override
    public ResponseEntity<Object> modifyMemberProfile(MemberModifyObj member) {
        /**
         * 이미지 저장 로직 추가 - start
         */
        Files files = new Files();

        if (!member.isDefaultImage() && member.getUploadedFile() != null) {
            deleteBeforeProfileImage(member.getIdx());
            MultipartFile multipartFile = member.getUploadedFile();
            // 파일저장
            String fileName = this.amazonClient.uploadFile(multipartFile, this.memberPath);
            String thumb = this.amazonClient.uploadFileThumbnail(multipartFile, this.memberThumbnailPath);
            String[] splits = multipartFile.getOriginalFilename().split("\\.");


            files.setFileName(fileName);
            files.setFileThumbnail(thumb);
            files.setFileType(splits[1]);

            this.filesRepository.saveFile(files);
            log.info("saved_file " + files.getIdx());
            member.setFileIdx(files.getIdx());
        } else {
            if (member.isDefaultImage()){
                deleteBeforeProfileImage(member.getIdx());
                member.setFileIdx(0);
            }
        }
        /**
         * 이미지 저장 로직 추가 - end
         */
        int rtn = memberRepository.modifyMemberInfo(member);
        Map<String, Object> result = new HashMap<>();
        if (rtn > 0) {
            result.put("modifyMember", true);
        } else {
            result.put("modifyMember", false);
        }

        return ResponseEntityUtil.makeResultSuccess(result);
    }

    private void deleteBeforeProfileImage(int idx) {
        Member memberCheck = memberRepository.findByIdx(idx);
        if (memberCheck.getFileIdx() > 0) {
            Files beforeFiles = this.filesRepository.findByIdx(memberCheck.getFileIdx());
            this.amazonClient.deleteFileFromS3Bucket(beforeFiles.getFileName(), this.memberPath);
            this.amazonClient.deleteFileFromS3Bucket(beforeFiles.getFileThumbnail(), this.memberThumbnailPath);
            this.filesRepository.deleteByIdx(beforeFiles.getIdx());
        }
    }

    @Override
    public ResponseEntity<Object> saveMemberYear(Member member) {

        int rtn = this.memberRepository.saveMemberYear(member);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> getAdminCheckUsers() {
        List<SuggestMember> adminCheckUser = this.memberRepository.findByAdminCheckYn();
        return ResponseEntityUtil.makeResultSuccess(adminCheckUser);
    }

    @Override
    public ResponseEntity<Object> getASuggestUsers(List<Integer> users) {
        List<SuggestMember> getSuggestUsers = this.memberRepository.findBySuggestUserIdxs(users);
        return ResponseEntityUtil.makeResultSuccess(getSuggestUsers);
    }

    @Override
    public void changeUserMailCert(String encrypt) throws GeneralSecurityException, UnsupportedEncodingException, DecoderException {
        AES256Util aes256Util = new AES256Util("avansoft-dev-lab");
        URLCodec codec = new URLCodec();
        log.info("encrypt===" + encrypt);
        String data = codec.decode(encrypt);
        log.info("data===" + data);
        String change1 = aes256Util.decrypt(data);
        log.info("change1===" + change1);
        String change2 = change1.replaceAll(" ", "");
        log.info("change2===" + change2);

        int memberIdx = Integer.parseInt(change2);
        this.memberRepository.changeCertMail(memberIdx);


    }

    @Override
    public ResponseEntity<Object> sendCertMailToUser(Member find) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        Member member = this.memberRepository.findByIdx(find.getIdx());
        Account account = this.accountRepository.findByUserIdx(member.getIdx());


        MailDto mailDto = new MailDto();
        List<String> to = new ArrayList<>();
        to.add(account.getEmail());

        mailDto.setTo(to);
        mailDto.setFrom(this.mailAdmin);
        mailDto.setSubject(this.sendMailService.titleVerify());
        mailDto.setHtmlBody(this.sendMailService.contentVerification(member));
        mailDto.setContent("VORA의 계정 인증 메일입니다.");

        Boolean checker = this.sendMailService.send(mailDto);
        if (checker) {
            return ResponseEntityUtil.makeResultSuccess("");
        } else {
            return ResponseEntityUtil.makeResultError("");
        }

    }

    @Override
    public ResponseEntity<Object> sendTempPwd(Account find, HttpServletRequest request) throws UnsupportedEncodingException, GeneralSecurityException, EncoderException {
        Account account = this.accountRepository.findByEmail(find.getEmail());
        Member member = this.memberRepository.findByIdx(account.getUserIdx());
        String tempPassword = "";

        int pwdLength = 16;
        final char[] passwordTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
                '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        Random random = new Random(System.currentTimeMillis());
        int tablelength = passwordTable.length;
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < pwdLength; i++) {
            buf.append(passwordTable[random.nextInt(tablelength)]);
        }
        tempPassword = buf.toString();

        account.setUserPwd(SHA256.getHash(tempPassword));
        accountRepository.updateAccountPwd(account);

        MailDto mailDto = new MailDto();
        List<String> to = new ArrayList<>();
        to.add(account.getEmail());

        mailDto.setTo(to);
        mailDto.setFrom(this.mailAdmin);
        mailDto.setSubject(this.sendMailService.titlePassword());
        mailDto.setHtmlBody(this.sendMailService.changePwdMail(member, tempPassword));
        mailDto.setContent("VORA의 계정 인증 메일입니다.");

        Boolean checker = this.sendMailService.send(mailDto);
        if (checker) {

            MemberActivity memberActivity = new MemberActivity();
            memberActivity.setActivityComment("임시 비밀번호 요청");
            memberActivity.setActivityType("Temp");
            String userIp = CommonUtils.checkIp(request);
            memberActivity.setIp(userIp);
            memberActivity.setMemberIdx(account.getUserIdx());

            this.memberActivityRepository.saveActivity(memberActivity);


            return ResponseEntityUtil.makeResultSuccess("");
        } else {
            return ResponseEntityUtil.makeResultError("");
        }
    }

    @Override
    public ResponseEntity<Object> checkUserEmail(Account member) {
        Account account = this.accountRepository.findByEmail(member.getEmail());
        if (account != null) {
            return ResponseEntityUtil.makeResultSuccess("");
        } else {
            return ResponseEntityUtil.makeResultError(ErrorMessages.NOT_JOINDED_USER.getCode());
        }
    }

    @Override
    public void changeUserStatusMail(String encrypt) throws DecoderException, UnsupportedEncodingException, GeneralSecurityException {
        AES256Util aes256Util = new AES256Util("avansoft-dev-lab");
        URLCodec codec = new URLCodec();
        log.info("encrypt===" + encrypt);
        String data = codec.decode(encrypt);
        log.info("data===" + data);
        String change1 = aes256Util.decrypt(data);
        log.info("change1===" + change1);
        String change2 = change1.replaceAll(" ", "");
        log.info("change2===" + change2);

        int memberIdx = Integer.parseInt(change2);
        this.memberRepository.changeUserStatus(memberIdx);
    }

    @Override
    public ResponseEntity<Object> memberExit(Member member) {
        Member find = this.memberRepository.findByIdx(member.getIdx());

        List<Feeds> feedImageDelList = this.feedsRepository.findByMemberIdx(find);

        for(Feeds feeds : feedImageDelList){
            List<Files> getFiles = this.filesRepository.findByFeedIdx(feeds.getIdx());

            for(Files files : getFiles){
                this.amazonClient.deleteFileFromS3Bucket(files.getFileName(), feedPath);
                this.amazonClient.deleteFileFromS3Bucket(files.getFileThumbnail(), feedThumbnailPath);
            }
        }

        // 타 사용자의 피드에 내가 언급된 내용 변경처리
        HashMap<String, Object> changeMemberTags = new HashMap<>();
        String findtag = "ꊞ";
        String changetag = "@";
        changeMemberTags.put("findtag",findtag + member.getIdx());
        changeMemberTags.put("changeNick", changetag + find.getNickName());

        this.feedsRepository.updateDelMemberChange(changeMemberTags);
        this.feedCommentRepository.updateDelMemberChange(changeMemberTags);



        Files members = this.filesRepository.findByIdx(find.getFileIdx());
        if(member.getFileIdx() != 0){
            this.amazonClient.deleteFileFromS3Bucket(members.getFileName(), feedPath);
            this.amazonClient.deleteFileFromS3Bucket(members.getFileThumbnail(), feedThumbnailPath);
        }


        DeleteMember deleteMember = new DeleteMember();
        BeanUtils.copyProperties(find, deleteMember);
        this.deleteMemberRepository.insertDeleteMember(deleteMember);

        this.memberRepository.exitMember(member);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> checkDuplicateEmail(Account member) {
        HashMap<String, Object> check = new HashMap<>();
        int find = this.accountRepository.checkDuplicateMail(member);

        if(find > 0){
            check.put("mailCheck", false);
        }else{
            check.put("mailCheck", true);
        }


        return ResponseEntityUtil.makeResultSuccess(check);
    }

    private void saveAlarm(Alarm alarm) {
        this.alarmRepository.save(alarm);
    }
}
