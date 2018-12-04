package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.RecordViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordViews1MssqlRepository extends JpaRepository<RecordViews, Integer> {
}

