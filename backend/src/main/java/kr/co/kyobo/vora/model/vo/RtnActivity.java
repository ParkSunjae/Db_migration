package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.MemberActivity;
import lombok.Data;

import java.util.List;

@Data
public class RtnActivity {
    private List<MemberActivity> activitiys;
    private int page;
    private int total;

}
