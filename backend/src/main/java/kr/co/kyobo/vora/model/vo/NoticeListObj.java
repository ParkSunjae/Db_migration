package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Notice;
import lombok.Data;

@Data
public class NoticeListObj extends Notice {
    private boolean toggle=false;
}
