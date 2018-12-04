package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedMemberTag;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedMemberTagRespository {

    @Insert("INSERT " +
            "INTO " +
            "feed_member_tag " +
            "( " +
            " feed_idx, " +
            " u_idx " +
            ") " +
            "VALUES " +
            "( " +
            " #{feedIdx}, " +
            " #{uIdx} " +
            ") ")
    void save(FeedMemberTag feedMemberTag);


    @Select("SELECT m.* " +
            "FROM feed_member_tag fmt " +
            "LEFT OUTER JOIN member m " +
            "ON fmt.u_idx = m.idx " +
            "WHERE fmt.feed_idx = #{idx}")
    List<Member> findByFeedIdx(int idx);


    @Select("SELECT SQL_CALC_FOUND_ROWS distinct f.*   " +
            "            FROM feed_member_tag fmt   " +
            "            LEFT OUTER JOIN feeds f   " +
            "            ON fmt.feed_idx = f.idx   " +
            "            WHERE f.uidx = #{uIdx} " +
            "            union   " +
            "            (SELECT distinct f.*   " +
            "            FROM feed_member_tag fmt   " +
            "            LEFT OUTER JOIN feeds f   " +
            "            ON fmt.feed_idx = f.idx   " +
            "            WHERE fmt.u_idx = #{uIdx})" +
            " LIMIT #{offset}, #{limit}")
    List<Feeds> findByUIdx(FindFeedsListObj feeds);

    @Select("SELECT FOUND_ROWS()")
    int totalCountTogether();

    @Delete("DELETE FROM feed_tags WHERE feed_idx=#{idx} AND u_idx = #{intValue}")
    void removeByFeedIdxAndmemberIdx(int idx, int i);

    @Select("SELECT CASE WHEN count(idx) > 0 THEN 1 ELSE 0 END FROM feed_member_tag WHERE feed_idx=#{feedIdx} AND u_idx = #{uIdx}")
    int findFeedIdxAndUidx(FeedMemberTag feedMemberTag);
}
