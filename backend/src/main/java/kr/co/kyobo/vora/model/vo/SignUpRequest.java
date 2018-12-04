package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;

@Data
public class SignUpRequest {
    private Member member;
    private Account account;
}
