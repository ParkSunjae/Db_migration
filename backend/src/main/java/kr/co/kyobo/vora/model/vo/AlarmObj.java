package kr.co.kyobo.vora.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class AlarmObj {
    private int page;
    private int totalCount;
    private int uIdx;
    @JsonIgnore
    private int offset;
    private int limit;
    List<RtnAlarm> alarmList;
}
