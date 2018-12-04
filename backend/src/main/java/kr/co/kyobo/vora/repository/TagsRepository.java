package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.api.SearchTagList;
import kr.co.kyobo.vora.model.database.Tags;
import kr.co.kyobo.vora.model.vo.SignUpTags;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagsRepository {
    @Select("SELECT * FROM tags WHERE tag=#{tag}")
    Tags findByTag(Tags tags);
    @Select("SELECT * FROM tags WHERE idx=#{idx}")
    Tags findByIdx(int idx);
    @Select("<script>" +
            "SELECT * FROM tags WHERE " +
            "idx IN " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item.idx}" +
            "</foreach>" +
            "</script>")
    List<Tags> findByIdxMulti(List<Tags> tagsList);

    @Select("<script>" +
            "SELECT * FROM tags WHERE " +
            "tag IN " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item.tag}" +
            "</foreach>" +
            "</script>")
    List<Tags> findByTagMulti(List<Tags> tagsList);

    @Update("UPDATE tags SET hits=hits+1 WHERE idx=#{idx}")
    int updateHitsTag(Tags tags);

    @Update("UPDATE tags SET use_yn=IF(use_yn='Y','N','Y') WHERE idx=#{idx}")
    int toggleUseYN(Tags tags);
    @Update("UPDATE tags SET admin_check_yn=IF(admin_check_yn='Y','N','Y') WHERE idx=#{idx}")
    int toggleAdminCheckYN(Tags tags);

    @Insert("INSERT INTO tags (tag, hits) VALUES(#{tag}, #{hits})")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int save(Tags tags);

    @Insert("<script>" +
            "INSERT INTO tags (tag) VALUES " +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(#{item.tag})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int saveMulti(List<Tags> tags);

    @Update("UPDATE tags SET hits=#{hits} WHERE idx=#{idx}")
    void updateHitsCountTags(Tags finded);

    @Select("SELECT t.* " +
            "FROM feed_tags ft " +
            "LEFT OUTER JOIN tags t " +
            "ON ft.tags_idx = t.idx " +
            "WHERE ft.feed_idx = #{idx}")
    List<Tags> findByFeedIdx(int idx);


    @Select("SELECT SQL_CALC_FOUND_ROWS * FROM tags WHERE hits > 999 AND use_yn = 'Y' order by rand() LIMIT #{offset}, #{limit}")
    List<SignUpTags> signUpSuggestTags(SearchTagList searchTagList);

    @Select("SELECT FOUND_ROWS()")
    int signUpSuggestTagsTotal();

    @Select("SELECT idx,tag FROM most_used_tag_view ORDER BY rand() LIMIT 6")
    List<Tags> findTopTagsOnRandom();
}
