package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.ActionTagsMovie;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ActionTagMovieRepository {

    @Insert("<script>" +
            "INSERT INTO action_tags_movie " +
            "( " +
            " action_tags_idx , " +
            " actor , " +
            " director , " +
            " m_image , " +
            " m_link , " +
            " pub_date , " +
            " m_subtitle , " +
            " m_title , " +
            " userRating " +
            ") " +
            "VALUES " +
            "<foreach item='item' collection='list'  open='' separator=',' close= ''>" +
            "( " +
            " #{item.actionTagsIdx}, " +
            " #{item.actor}, " +
            " #{item.director}, " +
            " #{item.mImage}, " +
            " #{item.mLink}, " +
            " #{item.pubDate}, " +
            " #{item.mSubtitle}, " +
            " #{item.mTitle}, " +
            " #{item.userRating} " +
            " ) " +
            "</foreach>" +
            "</script>")
    void saveMulti(List<ActionTagsMovie> saveMovies);

    @Select("SELECT atm.*      " +
            "              FROM action_tags_movie atm     " +
            "              LEFT OUTER JOIN action_tags at " +
            "              ON at.idx = atm.action_tags_idx " +
            "              WHERE at.feed_idx = #{idx}")
    List<ActionTagsMovie> findByActionTagIdx(int idx);

    @Delete("<script>" +
            "DELETE FROM action_tags_movie WHERE idx in (SELECT temp.idx FROM (SELECT atb.idx " +
            "FROM action_tags_movie atb " +
            "INNER JOIN action_tags atg " +
            "ON atb.action_tags_idx = atg.idx " +
            "INNER JOIN feeds fs " +
            "ON atg.feed_idx = fs.idx " +
            "WHERE  " +
            "  fs.idx = #{idx} and atb.idx in ( " +
            "<foreach item='item' collection='list' separator=','>" +
            "  #{item,jdbcType=INTEGER}" +
            "</foreach>" +
            " )) as temp)" +
            "</script>")
    void removeMulti(HashMap<String, Object> param);
}
