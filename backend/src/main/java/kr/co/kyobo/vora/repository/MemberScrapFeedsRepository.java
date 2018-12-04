package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.MemberScrapFeeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberScrapFeedsRepository {
    @Select("SELECT " +
            "* " +
            "FROM " +
            "member_scrap_feeds " +
            "WHERE " +
            "feed_idx = #{feedsIdx}")
    List<MemberScrapFeeds> findByFeedIdx(int feedsIdx);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "member_scrap_feeds " +
            "WHERE " +
            "feed_idx = #{idx} AND u_idx = #{uIdx}")
    MemberScrapFeeds FindByFeedIdxAndUIdx(Feeds feeds);

    @Delete("DELETE FROM member_scrap_feeds WHERE feed_idx = #{idx} AND u_idx = #{uIdx}")
    int setFalseScrap(Feeds feeds);

    @Insert("INSERT " +
            "INTO " +
            "member_scrap_feeds " +
            "( " +
            " feed_idx, " +
            " u_idx " +
            ") " +
            "VALUES " +
            "( " +
            " #{idx}, " +
            " #{uIdx} " +
            ")")
    int setTrueScrap(Feeds feeds);

    @Select("SELECT SQL_CALC_FOUND_ROWS " +
            "f.*  " +
            "FROM member_scrap_feeds msf " +
            "LEFT OUTER JOIN feeds f " +
            "ON msf.feed_idx = f.idx " +
            "WHERE msf.u_idx = #{uIdx} " +
            "AND f.uidx NOT IN (SELECT t_idx FROM member_block_member WHERE o_idx=#{uIdx}) " +
            "ORDER BY f.create_at DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Feeds> findByUIdx(FindFeedsListObj feeds);

    @Select("SELECT FOUND_ROWS()")
    int totalCountMyScrap();
}
