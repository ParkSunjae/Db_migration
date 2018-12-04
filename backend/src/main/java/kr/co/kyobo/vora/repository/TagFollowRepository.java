package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.TagFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagFollowRepository {
    @Insert("INSERT INTO tag_follow (tag_idx,u_idx) VALUES" +
            "(" +
            "#{tagIdx},#{uIdx}" +
            ")")
    int save(TagFollow tagFollow);

    @Insert("<script>" +
            "INSERT INTO tag_follow (tag_idx,u_idx) VALUES" +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(" +
            "#{item.tagIdx}," +
            "#{item.uIdx}" +
            ")" +
            "</foreach>" +
            "</script>")
    int saveMulti(List<TagFollow> tagFollowList);

    @Delete("DELETE FROM tag_follow WHERE u_idx=#{uIdx} AND tag_idx=#{tagIdx}")
    int deleteTagFollow(TagFollow tagFollow);

    @Select("SELECT * FROM tag_follow WHERE u_idx=#{idx}")
    List<TagFollow> selectByUIdx(TagFollow tagFollow);

    @Select("SELECT * FROM tag_follow WHERE tag_idx=#{tagIdx}")
    List<TagFollow> selectByTagIdx(TagFollow tagFollow);

    @Select("SELECT COUNT(u_idx) cnt FROM tag_follow WHERE tag_idx=#{tagIdx}")
    int countByTagIdx(int tagIdx);

    @Select("SELECT COUNT(idx) FROM tag_follow WHERE u_idx=#{uIdx}")
    int countByUidx(TagFollow tagFollow);

    @Select("SELECT COUNT(idx) FROM tag_follow WHERE u_idx=#{uIdx} AND tag_idx=#{tagIdx}")
    int countByTagFollow(TagFollow tagFollow);
}
