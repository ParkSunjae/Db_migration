package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.FeedReport;
import kr.co.kyobo.vora.service.feedReport.FeedReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FeedReportController {
    @Autowired
    FeedReportService feedReportService;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEED_REPORT + UriMethod.SAVE_FEED_REPORT)
    public ResponseEntity<Object> saveFeedReport(@RequestBody FeedReport feedReport){
        return this.feedReportService.saveFeedReport(feedReport);
    }
}
