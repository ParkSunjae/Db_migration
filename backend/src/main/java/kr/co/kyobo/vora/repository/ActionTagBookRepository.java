package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.ActionTagsBook;
import kr.co.kyobo.vora.model.database.VoraBooks;
import kr.co.kyobo.vora.model.vo.RtnVoraBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ActionTagBookRepository {

    @Insert("<script>" +
            "INSERT INTO action_tags_book " +
            "( " +
            "action_tags_idx, " +
            "book_id " +
            ") " +
            "VALUES " +
            "<foreach item='item' collection='list' separator=','>" +
            "( " +
            " #{item.actionTagsIdx}, " +
            " #{item.bookId} " +
            " ) " +
            "</foreach>" +
            "</script>")
    void saveMulti(List<ActionTagsBook> saveBooks);

    @Select("SELECT vr.* , atb.action_tags_idx     " +
            "              FROM vora_books vr     " +
            "              LEFT OUTER JOIN action_tags_book atb     " +
            "              ON atb.book_id = vr.kyobo_id " +
            "              LEFT OUTER JOIN action_tags at " +
            "              ON at.idx = atb.action_tags_idx " +
            "              WHERE at.feed_idx = #{idx}")
    List<RtnVoraBook> findByActionTagIdx(int idx);

    @Delete("<script>" +
            "DELETE FROM action_tags_book WHERE idx in (SELECT temp.idx FROM (SELECT atb.idx " +
            "FROM action_tags_book atb " +
            "INNER JOIN action_tags atg " +
            "ON atb.action_tags_idx = atg.idx " +
            "INNER JOIN feeds fs " +
            "ON atg.feed_idx = fs.idx " +
            "WHERE  " +
            "  fs.idx = #{idx} and atb.book_id in ( " +
            "<foreach item='item' collection='list' separator=','>" +
            "   #{item,jdbcType=VARCHAR}" +
            "</foreach>" +
            " )) as temp)" +
            "</script>")
    void removeMultiBook(HashMap<String, Object> param);
}
