package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.MemberActivity;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import lombok.Data;

import java.util.List;

@Data
public class RtnLoginHistory {
    private List<MemberLoginHistory> loginHistoryList;
    private int page;
    private int total;

}
