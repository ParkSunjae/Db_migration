package kr.co.kyobo.vora.service.feedCommentReport;

import kr.co.kyobo.vora.model.database.FeedCommentReport;
import kr.co.kyobo.vora.model.database.FeedReport;
import org.springframework.http.ResponseEntity;

public interface FeedCommentReportService {
    ResponseEntity<Object> saveFeedCommentReport(FeedCommentReport feedCommentReport);
}
