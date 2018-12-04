package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Help;
import kr.co.kyobo.vora.model.database.Notice;
import lombok.Data;

@Data
public class HelpListObj extends Help {
    private boolean toggle=false;
}
