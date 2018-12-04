package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.Alarm;
import kr.co.kyobo.vora.model.vo.AlarmObj;
import kr.co.kyobo.vora.service.alarm.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ALARM + UriMethod.CALL_MY_ALARM)
    public ResponseEntity<Object> getMyNotice(@RequestBody Alarm alarm) {
        return this.alarmService.getMyNotice(alarm.getUIdx());
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ALARM + UriMethod.CALL_MY_ALARM_ALL)
    public ResponseEntity<Object> getMyNoticeAll(@RequestBody AlarmObj alarm) {
        return this.alarmService.getMyNoticeAll(alarm);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ALARM + UriMethod.CALL_MY_ALARM_COUNT)
    public ResponseEntity<Object> getMyNoticeCount(@RequestBody Alarm alarm) {
        return this.alarmService.getMyNoticeCount(alarm.getUIdx());
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.ALARM + UriMethod.CHANGE_ALARM_SHOW)
    public ResponseEntity<Object> updateNotice(@RequestBody Alarm alarm) {
        return this.alarmService.updateNotice(alarm);
    }

}
