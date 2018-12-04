package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedCommentReport;
import kr.co.kyobo.vora.model.database.FeedReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedCommentReportRepository {
    @Insert("insert into feed_comment_report ( " +
            "feed_comment_idx, " +
            "report_uidx, " +
            "type, " +
            "report, " +
            "comment, " +
            "report_check, " +
            "admin_contents " +
            ")values( " +
            "#{feedCommentIdx}, " +
            "#{reportUidx}, " +
            "#{type}, " +
            "#{report}, " +
            "#{comment}, " +
            "#{reportCheck}, " +
            "#{adminContents} " +
            ") ")
    int saveFeedCommentReport(FeedCommentReport feedCommentReport);

}
