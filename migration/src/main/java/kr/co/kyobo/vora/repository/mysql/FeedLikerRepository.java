package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.FeedAndMemberIdx;
import kr.co.kyobo.vora.model.database.mysql.FeedLiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedLikerRepository extends JpaRepository<FeedLiker, FeedAndMemberIdx> {
}
