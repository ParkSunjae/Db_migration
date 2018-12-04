package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Help;
import kr.co.kyobo.vora.model.vo.HelpListObj;
import kr.co.kyobo.vora.model.vo.HelpRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HelpRepository {

    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "FROM help " +
            "WHERE use_yn=#{useYn} ")
    int countByUserYN(HelpRequest helpRequest);

    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "FROM help")
    int countAll();

    @Select("SELECT * FROM help " +
            "WHERE use_yn=#{useYn} " +
            "ORDER BY idx desc" +
            " LIMIT #{offset}, #{limit}")
    List<HelpListObj> findByUseYn(HelpRequest helpRequest);

    @Select("SELECT * FROM help " +
            "ORDER BY idx desc" +
            " LIMIT #{offset}, #{limit}")
    List<HelpListObj> findAll(HelpRequest helpRequest);

    @Insert("<script>" +
            "INSERT INTO help " +
            "(title,content,use_yn) " +
            "values " +
            "(#{title},#{content},#{useYn})" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int insert(Help help);

    @Update("<script>" +
            "UPDATE help" +
            "<set>" +
            "<if test=\"title!=null\">title=#{title},</if>" +
            "<if test=\"content!=null\">content=#{content},</if>" +
            "<if test=\"useYn\">use_yn=#{useYn},</if>" +
            "idx=idx" +
            "</set>" +
            "WHERE idx=#{idx}" +
            "</script>")
    int update(Help help);

    @Delete("DELETE FROM help WHERE idx=#{idx}")
    int deleteByIdx(Help help);
}
