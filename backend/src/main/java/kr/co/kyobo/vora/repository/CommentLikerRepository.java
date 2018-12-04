package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.CommentLiker;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentLikerRepository {

    @Select("SELECT * FROM comment_liker WHERE comment_idx = #{idx}")
    List<CommentLiker> findByCommentIdx(int idx);

    @Select("SELECT * FROM comment_liker WHERE comment_idx = #{commentIdx} AND u_idx = #{uIdx}")
    CommentLiker checkLikeUser(CommentLiker commentLiker);

    @Delete("DELETE FROM comment_liker WHERE comment_idx = #{commentIdx} AND u_idx = #{uIdx}")
    int changeCommentLikeFalse(CommentLiker commentLiker);

    @Insert("INSERT INTO comment_liker ( comment_idx, u_idx ) VALUES ( #{commentIdx}, #{uIdx} )")
    int changeCommentLikeTrue(CommentLiker commentLiker);

    @Delete("DELETE FROM comment_liker WHERE comment_idx = #{idx}")
    int deleteByCommentIdx(int idx);

    @Delete("DELETE FROM comment_liker WHERE comment_idx in (" +
            "SELECT idx FROM feed_comment WHERE parent_idx = #{idx} " +
            " )")
    int deleteByCommentParentIdx(int idx);
}
