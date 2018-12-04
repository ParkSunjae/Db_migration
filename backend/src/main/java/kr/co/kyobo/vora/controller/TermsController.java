package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Terms;
import kr.co.kyobo.vora.service.terms.TermsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TermsController {

    @Autowired
    private TermsService termsService;

    @GetMapping(value = UriVersion.API_VERSION_PUBLIC + UriEntity.TERMS + UriMethod.FIND_JOIN_TERMS)
    public ResponseEntity<Object> getJoinTerms(){
        return this.termsService.findJoinTerms();
    }

    @PostMapping(value = UriVersion.API_VERSION_PUBLIC + UriEntity.TERMS + UriMethod.GET_ALL_TERMS)
    public ResponseEntity<Object> getAllTerms(@RequestBody Terms terms){
        return this.termsService.getAllTerms(terms);
    }

}
