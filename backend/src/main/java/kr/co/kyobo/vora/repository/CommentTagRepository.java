package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.CommentTags;
import kr.co.kyobo.vora.model.database.Tags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentTagRepository {

    @Select("SELECT * FROM comment_tags WHERE tag_idx = #{idx}")
    List<CommentTags> findByCommentIdx(int idx);

    @Select("SELECT t.* " +
            "FROM comment_tag ct " +
            "LEFT OUTER JOIN tags t " +
            "ON ct.tag_idx = t.idx " +
            "WHERE comment_idx = #{idx}")
    List<Tags> findTagByCommentIdx(int idx);

    @Select("SELECT * FROM comment_tag WHERE tag_idx = #{tagIdx} AND comment_idx = #{commentIdx}")
    CommentTags checkTagsUser(CommentTags commentTags);

    @Delete("DELETE FROM comment_tag WHERE tag_idx = #{tagIdx} AND comment_idx = #{commentIdx}")
    int changeCommentTagsFalse(CommentTags commentTags);

    @Insert("INSERT INTO comment_tag ( tag_idx, comment_idx ) VALUES ( #{tagIdx}, #{commentIdx} )")
    int changeCommentTagsTrue(CommentTags commentTags);

    @Delete("DELETE FROM comment_tag WHERE idx = #{idx}")
    int deleteByCommentIdx(int idx);

    @Delete("DELETE FROM comment_tag WHERE tag_idx in (" +
            "SELECT idx FROM feed_comment WHERE parent_idx = #{idx} " +
            " )")
    int deleteByCommentParentIdx(int idx);

    @Insert("<script>" +
            "INSERT INTO comment_tag ( tag_idx, comment_idx ) VALUES  " +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(#{item.tagIdx}, #{item.commentIdx})" +
            "</foreach>" +
            "</script>")
    void saveMulti(List<CommentTags> follwers);

    @Insert("INSERT INTO comment_tag ( tag_idx, comment_idx ) VALUES  " +
            "(#{tagIdx}, #{commentIdx})"
            )
    void save(CommentTags commentTags);
}
