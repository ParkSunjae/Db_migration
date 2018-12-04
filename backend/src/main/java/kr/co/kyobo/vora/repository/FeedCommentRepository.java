package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedComment;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.ResponseFeedComment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface FeedCommentRepository {
    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx}")
    List<FeedComment> findByFeedsIdx(int idx);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx}")
    List<ResponseFeedComment> findByFeedsIdxOnResonse(int idx);

    @Select("SELECT SQL_CALC_FOUND_ROWS" +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx} AND parent_idx = 0 ORDER BY likes DESC , create_at DESC limit #{offset}, #{limit}")
    List<FeedComment> findParentCommnetByFeedsIdxOrderByLikeCount(FindFeedsListObj findFeedsListObj);

    @Select("SELECT FOUND_ROWS()")
    int findParentCommnetByFeedsIdxOrderByLikeCountsTotalCount();

    @Select("SELECT SQL_CALC_FOUND_ROWS" +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx} AND parent_idx = 0 ORDER BY idx DESC limit #{offset}, #{limit}")
    List<FeedComment> findParentCommnetByFeedsIdxOrderByIdxAsc(FindFeedsListObj findFeedsListObj);

    @Select("SELECT FOUND_ROWS()")
    int findParentCommnetByFeedsIdxOrderByIdxAscTotalCount();

    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx} AND parent_idx = #{parentIdx} ORDER BY likes DESC")
    List<FeedComment> findChildCommnetByFeedsIdxOrderByLikeCount(FindFeedsListObj findFeedsListObj);


    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " feed_idx = #{idx} AND parent_idx = #{parentIdx} ORDER BY idx DESC")
    List<FeedComment> findChildCommnetByFeedsIdxOrderByIdxAsc(FindFeedsListObj findFeedsListObj);

    @Insert("INSERT INTO feed_comment (  " +
            " parent_idx, " +
            " feed_idx , " +
            " uidx , " +
            " comment_contents" +
            " ) " +
            "VALUES " +
            " ( " +
            " #{parentIdx} , " +
            " #{feedIdx} , " +
            " #{uIdx} , " +
            " #{commentContents} " +
            " ) ")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int saveParent(FeedComment feedComment);

    @Update("UPDATE feed_comment SET comment_contents = #{commentContents} WHERE idx = #{idx}")
    int updateComment(FeedComment feedComment);

    @Update("UPDATE feed_comment SET likes = likes + 1 WHERE idx = #{commentIdx}")
    void updateLikesTrueByCommentIdx(int commentIdx);

    @Update("UPDATE feed_comment SET likes = likes - 1 WHERE idx = #{commentIdx}")
    void updateLikesFalseByCommentIdx(int commentIdx);

    @Delete("Delete FROM feed_comment WHERE idx = #{idx}")
    void deleteByCommentIdx(FeedComment feedComment);

    @Delete("Delete FROM feed_comment WHERE parent_idx = #{idx}")
    void deleteByCommentChildIdx(FeedComment feedComment);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_comment " +
            "WHERE " +
            " idx = #{parentIdx}")
    FeedComment findByCommentIdx(int parentIdx);

    @Select("SELECT CASE WHEN count(idx) > 0 THEN 1 ELSE 0 END  " +
            "FROM feed_comment " +
            "WHERE feed_idx = #{idx} AND uidx = #{uIdx} ")
    int findMyCommentByFeedIdxAndUidx(Feeds finds);

    @Update("UPDATE feed_comment SET comment_contents = REPLACE (comment_contents, #{findtag} , #{changeNick})")
    void updateDelMemberChange(HashMap<String, Object> changeMemberTags);
}
