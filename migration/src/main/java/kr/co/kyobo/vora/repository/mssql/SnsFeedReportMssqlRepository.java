package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SnsFeedReportMssqlRepository extends JpaRepository<SnsFeedReport, Integer> {
}
