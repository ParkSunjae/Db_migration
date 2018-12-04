package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Notice;
import kr.co.kyobo.vora.model.vo.NoticeRequest;
import kr.co.kyobo.vora.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.NOTICE + UriMethod.GET_NOTICE_BY_USE_YN)
    public ResponseEntity<Object> getNoticeListByUseYn(@RequestBody NoticeRequest noticeRequest, HttpServletResponse response){
        return noticeService.getNoticeListByUseYn(noticeRequest,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.NOTICE + UriMethod.GET_NOTICE_ALL)
    public ResponseEntity<Object> getNoticeListAll(@RequestBody NoticeRequest noticeRequest, HttpServletResponse response){
        return noticeService.getNoticeListAll(noticeRequest,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.NOTICE + UriMethod.UPDATE_NOTICE)
    public ResponseEntity<Object> updateNotice(@RequestBody Notice notice, HttpServletResponse response){
        return noticeService.updateNotice(notice,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.NOTICE + UriMethod.INSERT_NOTICE)
    public ResponseEntity<Object> insertNotice(@RequestBody Notice notice, HttpServletResponse response){
        return noticeService.insertNotice(notice,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.NOTICE + UriMethod.DELETE_NOTICE)
    public ResponseEntity<Object> deleteNotice(@RequestBody Notice notice, HttpServletResponse response){
        return noticeService.deleteNotice(notice,response);
    }
}
