package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.LoginHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoginHistoryMssqlRepository extends CrudRepository<LoginHistory, Integer> {
    @Query(value= "SELECT DISTINCT strip FROM LoginHistory ")
    Iterable<String> findDistinctAll();

    @Query(value="SELECT idx, strid, strip, strcountry, strdevice, regdate FROM login_history WHERE strid = ?1" ,nativeQuery = true)
    Iterable<LoginHistory> findByStrid(String strId);
}
