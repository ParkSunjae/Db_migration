package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedReportRepository {
    @Insert("insert into feed_report ( " +
            "feeds_idx, " +
            "report_uidx, " +
            "type, " +
            "report, " +
            "comment, " +
            "report_check, " +
            "admin_contents " +
            ")values( " +
            "#{feedsIdx}, " +
            "#{reportUidx}, " +
            "#{type}, " +
            "#{report}, " +
            "#{comment}, " +
            "#{reportCheck}, " +
            "#{adminContents} " +
            ") ")
    int saveFeedReport(FeedReport feedReport);

}
