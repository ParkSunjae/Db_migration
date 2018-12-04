package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.MemberAdminRecommend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAdminRecommendRepository extends CrudRepository<MemberAdminRecommend, Integer> {

}
