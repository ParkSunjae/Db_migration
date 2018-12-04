package kr.co.kyobo.vora.service.memberInquiry;

import kr.co.kyobo.vora.model.database.MemberBlockMember;
import kr.co.kyobo.vora.model.database.MemberInquiry;
import kr.co.kyobo.vora.model.vo.MemberBlockMemberRequest;
import kr.co.kyobo.vora.model.vo.MemberInquiryRequest;
import org.springframework.http.ResponseEntity;

public interface MemberInquiryService {
    ResponseEntity<Object> saveInquiry(MemberInquiry memberInquiry);
    ResponseEntity<Object> updateInquiry(MemberInquiry memberInquiry);
    ResponseEntity<Object> findByRequest(MemberInquiryRequest memberInquiryRequest);

}