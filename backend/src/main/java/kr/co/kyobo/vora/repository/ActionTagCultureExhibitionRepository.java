package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.ActionTagsCultureExhibition;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ActionTagCultureExhibitionRepository {


    @Insert("<script>" +
            "INSERT INTO acrtion_tags_culture_exhibition " +
            "( " +
            " action_tags_idx , " +
            " id , " +
            " cateName1 , " +
            " c_image , " +
            " c_link , " +
            " c_location , " +
            " startDate , " +
            " endDate , " +
            " c_title  " +
            ") " +
            "VALUES " +
            "<foreach item='item' collection='list' separator=',' >" +
            "( " +
            " #{item.actionTagsIdx} , " +
            " #{item.id} , " +
            " #{item.cateName1} , " +
            " #{item.cImage} , " +
            " #{item.cLink} , " +
            " #{item.cLocation} , " +
            " #{item.startDate} , " +
            " #{item.endDate} , " +
            " #{item.cTitle}  " +
            " ) " +
            "</foreach>" +
            "</script>")
    void saveMulti(List<ActionTagsCultureExhibition> saveMovies);

    @Select("SELECT atce.*      " +
            "              FROM acrtion_tags_culture_exhibition atce     " +
            "              LEFT OUTER JOIN action_tags at " +
            "              ON at.idx = atce.action_tags_idx " +
            "              WHERE at.feed_idx = #{idx}")
    List<ActionTagsCultureExhibition> findByActionTagIdx(int idx);

    @Delete("<script>" +
            "DELETE FROM acrtion_tags_culture_exhibition WHERE idx in (SELECT temp.idx FROM (SELECT atb.idx " +
            "FROM acrtion_tags_culture_exhibition atb " +
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
