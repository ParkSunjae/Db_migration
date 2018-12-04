package kr.co.kyobo.vora.service.notice;

import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Notice;
import kr.co.kyobo.vora.model.vo.NoticeListObj;
import kr.co.kyobo.vora.model.vo.NoticeRequest;
import kr.co.kyobo.vora.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<Object> getNoticeListAll(NoticeRequest noticeRequest, HttpServletResponse response) {
        noticeRequest.setTotal(noticeRepository.countAll());
        noticeRequest = setPage(noticeRequest);

        List<NoticeListObj> noticeList = noticeRepository.findAll(noticeRequest);
        noticeRequest.setNoticeList(noticeList);
        return ResponseEntityUtil.makeResultSuccess(noticeRequest);
    }

    @Override
    public ResponseEntity<Object> getNoticeListByUseYn(NoticeRequest noticeRequest, HttpServletResponse response) {
        noticeRequest.setTotal(noticeRepository.countByUserYN(noticeRequest));
        noticeRequest = setPage(noticeRequest);

        List<NoticeListObj> noticeList = noticeRepository.findByUseYn(noticeRequest);
        noticeRequest.setNoticeList(noticeList);
        return ResponseEntityUtil.makeResultSuccess(noticeRequest);
    }

    @Override
    public ResponseEntity<Object> insertNotice(Notice notice, HttpServletResponse response) {
        int rst = noticeRepository.insert(notice);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",notice);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> updateNotice(Notice notice, HttpServletResponse response) {
        int rst = noticeRepository.update(notice);
        if(rst>0){
            Map<String,Object> result = new HashMap<>();
            result.put("data",notice);
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getCode());
        }
    }

    @Override
    public ResponseEntity<Object> deleteNotice(Notice notice, HttpServletResponse response) {
        int result = noticeRepository.deleteByIdx(notice);
        if(result>0){
            return ResponseEntityUtil.makeResultSuccess(result);
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_DELETE_FAIL.getCode());
        }
    }

    private NoticeRequest setPage(NoticeRequest noticeRequest) {
        if(noticeRequest.getPage() > 1){
            if(noticeRequest.getTotal() < noticeRequest.getPage() * noticeRequest.getLimit()){
                noticeRequest.setPage(
                        noticeRequest.getTotal()/noticeRequest.getLimit()
                        + (noticeRequest.getTotal()%noticeRequest.getLimit()>0?1:0)
                );
            }
            noticeRequest.setOffset((noticeRequest.getPage() - 1) * noticeRequest.getLimit());
        }else{
            noticeRequest.setOffset((noticeRequest.getPage() - 1));
        }
        return noticeRequest;
    }


}
