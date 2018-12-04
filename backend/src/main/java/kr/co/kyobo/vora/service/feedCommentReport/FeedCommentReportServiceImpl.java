package kr.co.kyobo.vora.service.feedCommentReport;

import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.FeedCommentReport;
import kr.co.kyobo.vora.repository.FeedCommentReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedCommentReportServiceImpl implements FeedCommentReportService {

    @Autowired
    FeedCommentReportRepository feedCommentReportRepository;

    @Override
    public ResponseEntity<Object> saveFeedCommentReport(FeedCommentReport feedCommentReport) {
        int rst = feedCommentReportRepository.saveFeedCommentReport(feedCommentReport);
        if(rst>0){
            return ResponseEntityUtil.makeResultSuccess("");
        }else{
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        }
    }
}
