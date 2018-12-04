package kr.co.kyobo.vora.service.account;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.common.encrypt.SHA256;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberActivity;
import kr.co.kyobo.vora.model.vo.AccountPwd;
import kr.co.kyobo.vora.model.vo.RtnAccount;
import kr.co.kyobo.vora.repository.AccountRepository;
import kr.co.kyobo.vora.repository.MemberActivityRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberActivityRepository memberActivityRepository;

    @Override
    public ResponseEntity<Object> getUserAccount(Account account) {
        RtnAccount rtnAccount = new RtnAccount();
        Account find = this.accountRepository.findByAccountIdx(account.getIdx());
        Member findMember = this.memberRepository.findByIdx(find.getUserIdx());

        BeanUtils.copyProperties(find, rtnAccount);
        BeanUtils.copyProperties(findMember, rtnAccount);

        rtnAccount.setAccountCreateAt(find.getCreateAt());

        if (find != null) {
            return ResponseEntityUtil.makeResultSuccess(rtnAccount);
        } else {
            return ResponseEntityUtil.makeResultError("");
        }

    }

    @Override
    public ResponseEntity<Object> checkAccountPwdAndChange(AccountPwd account, HttpServletRequest request) {

        account.setUserPwd(SHA256.getHash(account.getUserPwd()));

        Account find = this.accountRepository.findByEmailAndPassword(account);
        if (find != null) {
            find.setUserPwd(SHA256.getHash(account.getNewPassword()));
            this.accountRepository.updateAccountPwd(find);

            MemberActivity memberActivity = new MemberActivity();
            memberActivity.setActivityComment("비밀번호 변경");
            memberActivity.setActivityType("Change");
            String userIp = CommonUtils.checkIp(request);
            memberActivity.setIp(userIp);
            memberActivity.setMemberIdx(find.getUserIdx());

            this.memberActivityRepository.saveActivity(memberActivity);

            return ResponseEntityUtil.makeResultSuccess("");
        } else {
            return ResponseEntityUtil.makeResultError("");
        }


    }
}
