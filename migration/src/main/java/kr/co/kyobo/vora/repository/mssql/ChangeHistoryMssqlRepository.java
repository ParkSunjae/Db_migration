package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.ChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChangeHistoryMssqlRepository extends JpaRepository<ChangeHistory, Integer> {
    @Query(value="Select idx, regdate, strip, change1, strid FROM change_history WHERE strid = ?1" , nativeQuery = true)
    Iterable<ChangeHistory> findByStrid(String strId);
}
