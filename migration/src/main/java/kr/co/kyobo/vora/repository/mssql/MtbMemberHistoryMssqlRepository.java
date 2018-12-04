package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.MtbMemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MtbMemberHistoryMssqlRepository extends JpaRepository<MtbMemberHistory, Integer> {
}
