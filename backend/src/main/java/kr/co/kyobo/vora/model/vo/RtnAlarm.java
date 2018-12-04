package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Alarm;
import lombok.Data;

@Data
public class RtnAlarm extends Alarm {
    private String fileThumbnail;
}
