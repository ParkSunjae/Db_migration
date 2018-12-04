package kr.co.kyobo.vora.service.memberActivity;

import kr.co.kyobo.vora.model.api.RequestParam;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.MemberActivity;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import kr.co.kyobo.vora.model.vo.RtnActivity;
import kr.co.kyobo.vora.model.vo.RtnLoginHistory;
import kr.co.kyobo.vora.repository.MemberActivityRepository;
import kr.co.kyobo.vora.repository.MemberLoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberActivityServiceImpl implements MemberActivityService {

    @Autowired
    private MemberActivityRepository memberActivityRepository;

    @Autowired
    private MemberLoginHistoryRepository memberLoginHistoryRepository;

    @Override
    public ResponseEntity<Object> getActionList(RequestParam requestParam) {
        this.setPage(requestParam);

        List<MemberActivity> getList = this.memberActivityRepository.getActivities(requestParam);
        int total = this.memberActivityRepository.getTotal();

        RtnActivity rtnActivity = new RtnActivity();
        rtnActivity.setActivitiys(getList);
        rtnActivity.setPage(requestParam.getPage());
        rtnActivity.setTotal(total);

        return ResponseEntityUtil.makeResultSuccess(rtnActivity);
    }

    @Override
    public ResponseEntity<Object> getLoginLogList(RequestParam requestParam) {
        this.setPage(requestParam);
        List<MemberLoginHistory> memberLoginHistoryList = this.memberLoginHistoryRepository.getLoginHistory(requestParam);
        int total = this.memberLoginHistoryRepository.getTotal();

        RtnLoginHistory rtnLoginHistory = new RtnLoginHistory();
        rtnLoginHistory.setLoginHistoryList(memberLoginHistoryList);
        rtnLoginHistory.setPage(requestParam.getPage());
        rtnLoginHistory.setTotal(total);

        return ResponseEntityUtil.makeResultSuccess(rtnLoginHistory);
    }

    private void setPage(RequestParam requestParam) {
        if(requestParam.getPage() > 1){
            requestParam.setOffset((requestParam.getPage() - 1) * requestParam.getLimit());
        }else{
            requestParam.setOffset((requestParam.getPage() - 1));
        }
    }
}
