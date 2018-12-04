package kr.co.kyobo.vora.service.alarm;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Alarm;
import kr.co.kyobo.vora.model.vo.AlarmObj;
import kr.co.kyobo.vora.model.vo.RtnAlarm;
import kr.co.kyobo.vora.repository.AlarmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public ResponseEntity<Object> getMyNotice(int userIdx) {
        List<RtnAlarm> notShowList =  this.alarmRepository.findByUserIdx(userIdx);

        return ResponseEntityUtil.makeResultSuccess(notShowList);
    }

    @Override
    public ResponseEntity<Object> getMyNoticeAll(AlarmObj alarmObj) {
        this.setPage(alarmObj);
        List<RtnAlarm> notShowList =  this.alarmRepository.findByUserIdxAll(alarmObj);
        alarmObj.setTotalCount(this.alarmRepository.getCount());
        alarmObj.setAlarmList(notShowList);
        return ResponseEntityUtil.makeResultSuccess(alarmObj);
    }
    private void setPage(AlarmObj alarmObj) {
        if (alarmObj.getPage() > 1) {
            alarmObj.setOffset((alarmObj.getPage() - 1) * alarmObj.getLimit());
        } else {
            alarmObj.setOffset((alarmObj.getPage() - 1));
        }
    }

    @Override
    public ResponseEntity<Object> getMyNoticeCount(int uIdx) {
        int count =  this.alarmRepository.findByUserIdxForCount(uIdx);
        HashMap<String, Object> result = new HashMap<>();
        result.put("alarmCount",count);
        return ResponseEntityUtil.makeResultSuccess(result);
    }

    @Override
    public ResponseEntity<Object> updateNotice(Alarm alarm) {
        int count =  this.alarmRepository.updateShowYnByIdx(alarm);
        return ResponseEntityUtil.makeResultSuccess("");
    }
}
