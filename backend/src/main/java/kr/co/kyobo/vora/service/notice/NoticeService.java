package kr.co.kyobo.vora.service.notice;

import kr.co.kyobo.vora.model.database.Notice;
import kr.co.kyobo.vora.model.database.TagFollow;
import kr.co.kyobo.vora.model.vo.NoticeRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface NoticeService {
    ResponseEntity<Object> getNoticeListByUseYn(NoticeRequest noticeRequest, HttpServletResponse response);

    ResponseEntity<Object> getNoticeListAll(NoticeRequest noticeRequest, HttpServletResponse response);

    ResponseEntity<Object> updateNotice(Notice notice, HttpServletResponse response);

    ResponseEntity<Object> insertNotice(Notice notice, HttpServletResponse response);

    ResponseEntity<Object> deleteNotice(Notice notice, HttpServletResponse response);
}
