package kr.co.kyobo.vora.service.memberAndAccount;

import kr.co.kyobo.vora.model.database.mssql.MtbMember2;
import kr.co.kyobo.vora.model.database.mysql.Member;

import java.io.IOException;

public interface MakeMemberAndAccountService {
    void makeMemberAndAccount() throws IOException;
    void memberLoginAndChangeHistory(Member member, MtbMember2 mtbMember2) throws IOException;
}
