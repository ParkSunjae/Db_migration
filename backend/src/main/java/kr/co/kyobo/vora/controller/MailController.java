package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Slf4j
@CrossOrigin("*")
@Controller
public class MailController {

    @Value("${base.mail.cert.uri.frontend}")
    String baseUri;

    @Autowired
    private MemberService memberService;

    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MAIL + UriMethod.MAIL_VERIFICATION)
    public ModelAndView checkVerifyMail(@RequestParam("encrypt") String encrypt, HttpServletResponse response) throws GeneralSecurityException, IOException, DecoderException {
        this.memberService.changeUserMailCert(encrypt);
        return new ModelAndView("redirect:"+baseUri);
    }

    @PostMapping(UriVersion.API_VERSION_PUBLIC + UriEntity.MAIL + UriMethod.CHANGE_STATUS)
    public ModelAndView changeUserStatusMail(@RequestParam("encrypt") String encrypt, HttpServletResponse response) throws GeneralSecurityException, IOException, DecoderException {
        this.memberService.changeUserStatusMail(encrypt);
        return new ModelAndView("redirect:"+baseUri);
    }

}
