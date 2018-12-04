package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.service.businessCategory1.BusinessCategory1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BusinessCategory1Controller {
    @Autowired
    private BusinessCategory1Service businessCategory1Service;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.BUSINESS_CATEGORY1 + UriMethod.BUSINESSCATEGORY1_LIST)
    public ResponseEntity<Object> getList(){
        return this.businessCategory1Service.getList();
    }
}
