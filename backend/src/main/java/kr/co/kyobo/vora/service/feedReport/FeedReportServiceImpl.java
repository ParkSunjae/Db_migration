package kr.co.kyobo.vora.service.feedReport;

import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.RecommendResultData;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.FeedReport;
import kr.co.kyobo.vora.repository.FeedReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedReportServiceImpl implements FeedReportService {

    @Autowired
    FeedReportRepository feedReportRepository;

    @Override
    public ResponseEntity<Object> saveFeedReport(FeedReport feedReport) {
        int rst = feedReportRepository.saveFeedReport(feedReport);
        if(rst>0){
            return ResponseEntityUtil.makeResultSuccess("");
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        }
    }
}
