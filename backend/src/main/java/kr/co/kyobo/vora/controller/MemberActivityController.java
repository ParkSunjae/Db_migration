package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.api.RequestParam;
import kr.co.kyobo.vora.service.memberActivity.MemberActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberActivityController {

    @Autowired
    private MemberActivityService memberActivityService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_ACTIVITY + UriMethod.GET_ACTION_LIST)
    public ResponseEntity<Object> getActionList(@RequestBody RequestParam requestParam){
        return this.memberActivityService.getActionList(requestParam);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.MEMBER_ACTIVITY + UriMethod.GET_LOGIN_LOG_LIST)
    public ResponseEntity<Object> getLoginLogList(@RequestBody RequestParam requestParam){
        return this.memberActivityService.getLoginLogList(requestParam);
    }
}
