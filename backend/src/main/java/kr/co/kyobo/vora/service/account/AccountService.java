package kr.co.kyobo.vora.service.account;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.vo.AccountPwd;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    ResponseEntity<Object> getUserAccount(Account account);

    ResponseEntity<Object> checkAccountPwdAndChange(AccountPwd account, HttpServletRequest request);
}
