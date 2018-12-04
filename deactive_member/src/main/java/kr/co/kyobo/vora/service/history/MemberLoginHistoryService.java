package kr.co.kyobo.vora.service.history;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;

import java.util.List;

public interface MemberLoginHistoryService {
    List<MemberLoginHistory> findByMemberIdxByLast();
}
