package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberAdminRecommend {
    private int idx;
    private int uIdx;
    private String startDate;
    private String endDate;
    private String content;
    private String createAt;
    private int count;
}
