package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Help;
import kr.co.kyobo.vora.model.vo.HelpRequest;
import kr.co.kyobo.vora.service.help.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HelpController {
    @Autowired
    HelpService helpService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.HELP + UriMethod.GET_HELP_BY_USE_YN)
    public ResponseEntity<Object> getHelpListByUseYn(@RequestBody HelpRequest noticeRequest, HttpServletResponse response){
        return helpService.getHelpListByUseYn(noticeRequest,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.HELP + UriMethod.GET_HELP_ALL)
    public ResponseEntity<Object> getHelpListAll(@RequestBody HelpRequest noticeRequest, HttpServletResponse response){
        return helpService.getHelpListAll(noticeRequest,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.HELP + UriMethod.UPDATE_HELP)
    public ResponseEntity<Object> updateHelp(@RequestBody Help notice, HttpServletResponse response){
        return helpService.updateHelp(notice,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.HELP + UriMethod.INSERT_HELP)
    public ResponseEntity<Object> insertHelp(@RequestBody Help notice, HttpServletResponse response){
        return helpService.insertHelp(notice,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.HELP + UriMethod.DELETE_HELP)
    public ResponseEntity<Object> deleteHelp(@RequestBody Help notice, HttpServletResponse response){
        return helpService.deleteHelp(notice,response);
    }
}
