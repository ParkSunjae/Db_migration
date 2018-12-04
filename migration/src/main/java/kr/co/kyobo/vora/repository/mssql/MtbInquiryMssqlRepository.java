package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.MtbInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MtbInquiryMssqlRepository extends JpaRepository<MtbInquiry, Integer> {
    @Query(value = "SELECT * FROM mTb_Inquiry WHERE strUserId = ?1", nativeQuery = true)
    Iterable<MtbInquiry> findByStrUserId(String strId);
}
