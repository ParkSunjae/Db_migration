package kr.co.kyobo.vora.service.alarm;

import kr.co.kyobo.vora.model.database.Alarm;
import kr.co.kyobo.vora.model.vo.AlarmObj;
import org.springframework.http.ResponseEntity;

public interface AlarmService {
    ResponseEntity<Object> getMyNotice(int userIdx);

    ResponseEntity<Object> getMyNoticeAll(AlarmObj alarmObj);

    ResponseEntity<Object> getMyNoticeCount(int uIdx);

    ResponseEntity<Object> updateNotice(Alarm alarm);
}
