package kr.co.kyobo.vora.model.api;

import lombok.Data;

@Data
public class RequestParam {
    private int offset;
    private int limit;

    private String searchKind;
    private String searchWord;

    private int memberIdx;
    private String startDate;
    private String endDate;

    private int page;
    private int totalPage;
    private int perPageCount;

}
