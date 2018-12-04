package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.BusinessCategory2;
import kr.co.kyobo.vora.service.businessCategory2.BusinessCategory2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BusinessCategory2Controller {
    @Autowired
    private BusinessCategory2Service businessCategory2Service;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.BUSINESS_CATEGORY2 + UriMethod.BUSINESSCATEGORY2_LIST)
    public ResponseEntity<Object> postWrite(@RequestBody BusinessCategory2 businessCategory2){
        return this.businessCategory2Service.getListByParentIdx(businessCategory2);
    }
}
