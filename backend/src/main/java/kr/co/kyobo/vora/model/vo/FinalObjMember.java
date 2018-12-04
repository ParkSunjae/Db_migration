package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Files;
import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;

import java.util.List;

@Data
public class FinalObjMember {
    private Member member;
    private Files files;
    private Account account;
    private List<ResponseMyFeeds> rtns;
    private int page;
    private int totalCount;
    private int limit;
    private int followCount;
    private int followerCount;
}
