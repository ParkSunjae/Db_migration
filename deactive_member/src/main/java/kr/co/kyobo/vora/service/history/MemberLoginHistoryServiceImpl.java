package kr.co.kyobo.vora.service.history;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import kr.co.kyobo.vora.repository.MemberLoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberLoginHistoryServiceImpl implements MemberLoginHistoryService {

    @Autowired
    private MemberLoginHistoryRepository memberLoginHistoryRepository;

    @Override
    public List<MemberLoginHistory> findByMemberIdxByLast() {
        return this.memberLoginHistoryRepository.findByMembers();
    }
}
