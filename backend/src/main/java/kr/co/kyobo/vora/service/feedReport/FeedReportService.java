package kr.co.kyobo.vora.service.feedReport;

import kr.co.kyobo.vora.model.database.FeedReport;
import org.springframework.http.ResponseEntity;

public interface FeedReportService {
    ResponseEntity<Object> saveFeedReport(FeedReport feedReport);
}
