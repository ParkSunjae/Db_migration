package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.SearchHistory;
import kr.co.kyobo.vora.model.vo.PopularSearchKey;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchHistoryRepository {
    @Select("SELECT MAX(idx),search,MAX(create_at), " +
            "(SELECT b.visible FROM search_history b WHERE idx=a.idx) visible " +
            "FROM search_history a " +
            "WHERE visible ='Y' " +
            "GROUP BY uidx, search " +
            "ORDER BY idx " +
            "LIMIT 10")
    List<SearchHistory> findByUidx(int uIdx);

    @Insert("INSERT INTO search_history (" +
            "uidx, search, ip" +
            ") VALUES (" +
            "#{uIdx},#{search},#{ip}" +
            ")")
    int saveQueryHistory(SearchHistory searchHistory);

    @Select("SELECT search,count(idx) cnt FROM vora_db.search_history group by search limit 10;")
    List<PopularSearchKey> findPopularSearchKey();

    @Update("UPDATE search_history set visible='N' where idx=#{idx}")
    int deleteQueryHistory(SearchHistory searchHistory);

    @Update("UPDATE search_history set visible='N' where uidx=#{uIdx}")
    int deleteAllQueryHistory(SearchHistory searchHistory);
}
