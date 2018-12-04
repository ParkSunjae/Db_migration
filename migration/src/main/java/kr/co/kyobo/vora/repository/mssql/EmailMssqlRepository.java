package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmailMssqlRepository extends JpaRepository<Email, Integer> {
}
