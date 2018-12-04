package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.CommentMember;
import kr.co.kyobo.vora.model.database.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMemberRepository {

    @Select("SELECT * FROM comment_member WHERE comment_idx = #{idx}")
    List<CommentMember> findByCommentIdx(int idx);

    @Select("SELECT * FROM comment_member WHERE comment_idx = #{commentIdx} AND u_idx = #{uIdx}")
    CommentMember checkMemberUser(CommentMember commentMember);

    @Delete("DELETE FROM comment_member WHERE comment_idx = #{commentIdx} AND u_idx = #{uIdx}")
    int changeCommentMemberFalse(CommentMember commentMember);

    @Insert("INSERT INTO comment_member ( comment_idx, u_idx ) VALUES ( #{commentIdx}, #{uIdx} )")
    int changeCommentMemberTrue(CommentMember commentMember);

    @Delete("DELETE FROM comment_member WHERE comment_idx = #{idx}")
    int deleteByCommentIdx(int idx);

    @Delete("DELETE FROM comment_member WHERE comment_idx in (" +
            "SELECT idx FROM feed_comment WHERE parent_idx = #{idx} " +
            " )")
    int deleteByCommentParentIdx(int idx);

    @Insert("<script>" +
            "INSERT INTO comment_member ( comment_idx, u_idx ) VALUES  " +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(#{item.commentIdx}, #{item.uIdx})" +
            "</foreach>" +
            "</script>")
    void saveMulti(List<CommentMember> follwers);

    @Select("SELECT t.* " +
            "FROM comment_member ct " +
            "LEFT OUTER JOIN member t " +
            "ON ct.u_idx = t.idx " +
            "WHERE comment_idx = #{idx}")
    List<Member> findMemberByCommentIdx(int idx);
}
