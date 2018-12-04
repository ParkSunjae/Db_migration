package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Notice;
import kr.co.kyobo.vora.model.vo.NoticeListObj;
import kr.co.kyobo.vora.model.vo.NoticeRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeRepository {
    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "FROM notice " +
            "WHERE use_yn=#{useYn} ")
    int countByUserYN(NoticeRequest noticeRequest);

    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "FROM notice")
    int countAll();

    @Select("SELECT * FROM notice " +
            "WHERE use_yn=#{useYn} " +
            "ORDER BY idx desc" +
            " LIMIT #{offset}, #{limit}")
    List<NoticeListObj> findByUseYn(NoticeRequest noticeRequest);

    @Select("SELECT * FROM notice " +
            "ORDER BY idx desc" +
            " LIMIT #{offset}, #{limit}")
    List<NoticeListObj> findAll(NoticeRequest noticeRequest);

    @Insert("<script>" +
            "INSERT INTO notice " +
            "(title,content,use_yn) " +
            "values " +
            "(#{title},#{content},#{useYn})" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int insert(Notice notice);

    @Update("<script>" +
            "UPDATE notice" +
            "<set>" +
            "<if test=\"title!=null\">title=#{title},</if>" +
            "<if test=\"content!=null\">content=#{content},</if>" +
            "<if test=\"useYn\">use_yn=#{useYn},</if>" +
            "idx=idx" +
            "</set>" +
            "WHERE idx=#{idx}" +
            "</script>")
    int update(Notice notice);

    @Delete("DELETE FROM notice WHERE idx=#{idx}")
    int deleteByIdx(Notice notice);
}
