package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.database.FeedCommentReport;
import kr.co.kyobo.vora.model.database.FeedReport;
import kr.co.kyobo.vora.service.feedCommentReport.FeedCommentReportService;
import kr.co.kyobo.vora.service.feedReport.FeedReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FeedCommentReportController {
    @Autowired
    FeedCommentReportService feedCommentReportService;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEED_COMMENT_REPORT + UriMethod.SAVE_FEED_COMMENT_REPORT)
    public ResponseEntity<Object> saveFeedCommentReport(@RequestBody FeedCommentReport feedCommentReport){
        return this.feedCommentReportService.saveFeedCommentReport(feedCommentReport);
    }
}
