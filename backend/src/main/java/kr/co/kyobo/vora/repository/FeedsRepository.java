package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface FeedsRepository {

    @Insert("INSERT INTO feeds " +
            "(uidx,contents,link1) VALUES " +
            "(#{uIdx},#{contents},#{link1})")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int save(Feeds feeds);

    @Delete("DELETE FROM feeds WHERE idx=#{idx}")
    int delete(Feeds feeds);

    @Update("<script>" +
            "UPDATE feeds" +
            "<set>" +
            "<if test=\"contents!=null\">contents=#{contents},</if>" +
            "<if test=\"hits!=null\">hits=#{hits},</if>" +
            "<if test=\"likes!=null\">likes=#{likes},</if>" +
            "<if test=\"scrap!=null\">scrap=#{scrap},</if>" +
            "<if test=\"link1!=null\">link1=#{link1},</if>" +
            "<if test=\"link2!=null\">link2=#{link2},</if>" +
            "<if test=\"feedStatus!=null\">feed_status=#{feedStatus},</if>" +
            "<if test=\"tagLocation!=null\">tag_location=#{tagLocation},</if>" +
            "<if test=\"tagAction!=null\">tag_action=#{tagAction},</if>" +
            "idx=idx" +
            "</set>" +
            "WHERE idx=#{idx}" +
            "</script>")
    int updateFeed(Feeds feeds);

    @Update("UPDATE feeds SET likes=likes+1 where idx=#{idx}")
    int updateLikeFeed(Feeds feeds);

    @Update("UPDATE feeds SET hits=hits+1 where idx=#{idx}")
    int updateHitsFeed(Feeds feeds);

    @Select("SELECT * FROM feeds WHERE idx=#{idx}")
    Feeds findFeedsByIdx(Feeds feeds);

    @Select("<script>" +
            "SELECT * FROM feeds " +
            "<where>" +
            "idx IN " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{idx}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    List<Feeds> findFeedsByIdxMulti(List<Feeds> feeds);

    @Select("<script>" +
            "SELECT * FROM feeds " +
            "<where>" +
            "idx IN " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    List<Feeds> findFeedsByIdxMulti2(List<Integer> feeds);

    @Select("SELECT COUNT(idx) from feeds where uidx=#{idx}")
    int countByUidx(int idx);

    @Select("SELECT SQL_CALC_FOUND_ROWS  * FROM feeds WHERE uidx=#{uIdx} ORDER BY create_at DESC LIMIT #{offset}, #{limit}")
    List<Feeds> findByUIdx(FindFeedsListObj feeds);

    @Select("SELECT SQL_CALC_FOUND_ROWS DISTINCT f.* from feeds f " +
            "LEFT JOIN feed_tags ft ON f.idx = ft.feed_idx " +
            "LEFT JOIN feed_member_tag fmt ON f.idx = fmt.feed_idx " +
            "LEFT JOIN tag_follow tf ON ft.tags_idx = tf.tag_idx " +
            "LEFT JOIN member_follow_member mfm ON mfm.t_idx = f.uidx " +
            "WHERE (mfm.o_idx=#{uIdx} OR tf.u_idx=#{uIdx} OR f.uidx=#{uIdx} OR fmt.u_idx=#{uIdx}) " +
            "AND f.uidx NOT IN (SELECT t_idx FROM member_block_member WHERE o_idx=#{uIdx}) " +
            "ORDER BY create_at DESC LIMIT #{offset}, #{limit}")
    List<Feeds> findOnMain(FindFeedsListObj feeds);

    @Select("set @tagIdx := (SELECT GROUP_CONCAT(idx) FROM tags WHERE tag like concat('%',#{query},'%')); " +
            "set @memIdx := (SELECT GROUP_CONCAT(idx) FROM member WHERE nick_name like concat('%',#{query},'%')); " +
            "SELECT SQL_CALC_FOUND_ROWS * FROM feeds WHERE " +
            "idx in (" +
            "SELECT feed_idx FROM feed_tags WHERE tags_idx in (select @tagIdx)" +
            "UNION " +
            "SELECT feed_idx FROM feed_member_tag WHERE u_idx in (select @memIdx)" +
            "UNION " +
            "SELECT feed_idx FROM feed_comment WHERE idx in  " +
            "(" +
            "SELECT comment_idx FROM comment_tag WHERE tag_idx in (select @tagIdx) " +
            "UNION " +
            "SELECT comment_idx FROM comment_member WHERE u_idx in (select @memIdx)" +
            ")" +
            ") ORDER BY idx DESC limit #{offset}, #{limit}")
    List<Feeds> findByQuery(FindFeedsListObj findFeedsListObj);

    @Select("SELECT FOUND_ROWS()")
    int totalCountMyFeed();

    @Select("SELECT * FROM feeds WHERE uidx = #{idx} ")
    List<Feeds> findByMemberIdx(Member member);

    @Update("UPDATE feeds SET contents = REPLACE (contents, #{findtag} , #{changeNick})")
    void updateDelMemberChange(HashMap<String, Object> changeMemberTags);
}
