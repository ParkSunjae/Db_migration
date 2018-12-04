package kr.co.kyobo.vora.repository.mssql;

import kr.co.kyobo.vora.model.database.mssql.SnsFeedTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SnsFeedTagMssqlRepository extends JpaRepository<SnsFeedTag, Integer> {
    @Query(value = "SELECT a.* from (SELECT sft.tag_name,sum(sft.hits) hits, max(idx) idx, max(fidx) fidx, max(uid) uid, max(regdate) regdate, max(uname) uname " +
            "FROM sns_feed_tag sft " +
            "GROUP BY sft.tag_name) a " +
            "WHERE a.tag_name not in (SELECT st.tag FROM sns_tag st) ", nativeQuery = true)
    Page<SnsFeedTag> findAllUncontainSnsTag(Pageable pageable);
}
