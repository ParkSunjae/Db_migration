package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedTags;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.TagRelationFeedRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedTagsRepository {
    @Select("SELECT SQL_CALC_FOUND_ROWS DISTINCT f.* FROM " +
            "feed_tags ft " +
            "LEFT OUTER JOIN feeds f " +
            "ON ft.feed_idx = f.idx " +
            "WHERE f.uidx NOT IN (SELECT t_idx FROM member_block_member WHERE o_idx=#{uIdx}) " +
            "and ft.tags_idx = #{idx} " +
            "ORDER BY f.create_at DESC " +
            "LIMIT #{offset},#{limit}")
    List<Feeds> selectFeedsByTagidx(TagRelationFeedRequest tagRelationFeedRequest);

    @Select("SELECT SQL_CALC_FOUND_ROWS DISTINCT f.* FROM " +
            "feed_tags ft " +
            "LEFT OUTER JOIN feeds f " +
            "LEFT OUTER JOIN ( " +
            "SELECT feed_idx,COUNT(u_idx) like_cnt FROM feed_liker " +
            "WHERE create_at > (NOW() - INTERVAL 1 MONTH) " +
            "GROUP BY feed_idx " +
            ") like_order ON like_order.feed_idx = f.idx " +
            "ON ft.feed_idx = f.idx " +
            "WHERE f.uidx NOT IN (SELECT t_idx FROM member_block_member WHERE o_idx=#{uIdx}) " +
            "and ft.tags_idx = #{idx} " +
            "ORDER BY like_order.like_cnt DESC " +
            "LIMIT #{offset},#{limit}")
    List<Feeds> selectFeedsOnPopularByTagidx(TagRelationFeedRequest tagRelationFeedRequest);

    @Select("SELECT FOUND_ROWS()")
    int totalCount();

    @Insert("INSERT INTO feed_tags (feed_idx,tags_idx) VALUES (#{feedIdx},#{tagsIdx})")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int save(FeedTags feedTags);

    @Insert("<script>" +
            "INSERT INTO feed_tags (feed_idx,tags_idx) VALUES " +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(#{item.feedIdx},#{item.tagsIdx})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int saveMulti(List<FeedTags> tags);

    @Delete("DELETE FROM feed_tags WHERE feed_idx=#{feedIdx} AND tags_idx=#{tagsIdx}")
    int delete(FeedTags feedTags);

    @Delete("<script>" +
            "DELETE FROM feed_tags WHERE feed_idx=#{feedIdx} AND tags_idx IN " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item.tagsIdx}" +
            "</foreach>" +
            "</script>")
    int deleteMulti(FeedTags feedTags);

    @Delete("DELETE FROM feed_tags WHERE feed_idx=#{feedIdx}")
    int deleteByFeedIdx(FeedTags feedTags);
    @Delete("DELETE FROM feed_tags WHERE tags_idx=#{tagsIdx}")
    int deleteByTagsIdx(FeedTags feedTags);

    @Delete("DELETE FROM feed_tags WHERE feed_idx=#{idx} AND tags_idx = #{intValue}")
    void removeByFeedIdxAndTagIdx(int idx, int intValue);
}
