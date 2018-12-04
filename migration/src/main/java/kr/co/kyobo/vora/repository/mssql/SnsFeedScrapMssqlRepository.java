package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SnsFeedScrapMssqlRepository extends JpaRepository<SnsFeedScrap, Long> {
}
