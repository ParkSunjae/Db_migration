package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.vo.AccountPwd;
import kr.co.kyobo.vora.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 사용자 계정 정보 확인
     * @param account
     * @return
     */
    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ACCOUNT + UriMethod.GET_USER_ACCOUNT)
    public ResponseEntity<Object> getAccountByIdx(@RequestBody Account account){
        return this.accountService.getUserAccount(account);
    }


    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ACCOUNT + UriMethod.CHECK_PASSWORD)
    public ResponseEntity<Object> checkAccountPwd(@RequestBody AccountPwd account, HttpServletRequest request){
        return this.accountService.checkAccountPwdAndChange(account, request);
    }

}
