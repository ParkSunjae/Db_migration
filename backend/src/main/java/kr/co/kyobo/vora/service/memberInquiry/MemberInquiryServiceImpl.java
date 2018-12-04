package kr.co.kyobo.vora.service.memberInquiry;
import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.MemberInquiry;
import kr.co.kyobo.vora.model.vo.MemberInquiryRequest;
import kr.co.kyobo.vora.model.vo.NoticeListObj;
import kr.co.kyobo.vora.repository.MemberBlockMemberRepository;
import kr.co.kyobo.vora.repository.MemberInquiryRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class MemberInquiryServiceImpl implements MemberInquiryService {
    @Autowired
    MemberInquiryRepository memberInquiryRepository;

    @Override
    public ResponseEntity<Object> saveInquiry(MemberInquiry memberInquiry) {
        int rst = memberInquiryRepository.saveInquiry(memberInquiry);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",memberInquiry);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> updateInquiry(MemberInquiry memberInquiry) {
        int rst = memberInquiryRepository.updateInquiry(memberInquiry);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",memberInquiry);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> findByRequest(MemberInquiryRequest memberInquiryRequest) {
        memberInquiryRequest.setTotal(memberInquiryRepository.countAll(memberInquiryRequest));
        memberInquiryRequest = setPage(memberInquiryRequest);

        List<MemberInquiry> memberInquiryList = memberInquiryRepository.findByReqeust(memberInquiryRequest);
        memberInquiryRequest.setMemberInquiryList(memberInquiryList);
        return ResponseEntityUtil.makeResultSuccess(memberInquiryRequest);
    }

    private MemberInquiryRequest setPage(MemberInquiryRequest memberInquiryRequest) {
        if(memberInquiryRequest.getPage() > 1){

            if(memberInquiryRequest.getTotal() < memberInquiryRequest.getPage() * memberInquiryRequest.getLimit()){
                memberInquiryRequest.setPage(
                        memberInquiryRequest.getTotal()/ memberInquiryRequest.getLimit()
                                + (memberInquiryRequest.getTotal()% memberInquiryRequest.getLimit()>0?1:0)
                );
            }
            memberInquiryRequest.setOffset((memberInquiryRequest.getPage() - 1) * memberInquiryRequest.getLimit());
        }else{
            memberInquiryRequest.setOffset((memberInquiryRequest.getPage() - 1));
        }
        return memberInquiryRequest;
    }
}