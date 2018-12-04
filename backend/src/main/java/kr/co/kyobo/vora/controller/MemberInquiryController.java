package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.MemberInquiry;
import kr.co.kyobo.vora.model.vo.MemberInquiryRequest;
import kr.co.kyobo.vora.service.memberInquiry.MemberInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberInquiryController {
    @Autowired
    private MemberInquiryService memberInquiryService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.INQUIRY + UriMethod.SAVE_INQUIRY)
    public ResponseEntity<Object> saveInquiry(@RequestBody MemberInquiry memberInquiry){
        return this.memberInquiryService.saveInquiry(memberInquiry);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.INQUIRY + UriMethod.UPDATE_INQUIRY)
    public ResponseEntity<Object> updateInquiry(@RequestBody MemberInquiry memberInquiry){
        return this.memberInquiryService.updateInquiry(memberInquiry);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.INQUIRY + UriMethod.FIND_REQUEST_INQUIRY)
    public ResponseEntity<Object> findByRequest(@RequestBody MemberInquiryRequest memberInquiryRequest){
        return this.memberInquiryService.findByRequest(memberInquiryRequest);
    }
}
