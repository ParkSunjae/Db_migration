package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FilesRepository {
    @Select("SELECT * FROM files WHERE idx=#{idx}")
    Files findByIdx(int idx);

    @Delete("DELETE from files where idx=#{idx}")
    int deleteByIdx(int idx);

    @Insert("INSERT INTO files " +
            "( " +
            "file_name, " +
            "file_type, " +
            "file_thumbnail " +
            ") " +
            "VALUES ( " +
            "#{fileName}, " +
            "#{fileType}, " +
            "#{fileThumbnail} " +
            " )")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    void saveFile(Files files);

    @Select("SELECT f.* " +
            "FROM feed_files ff " +
            "LEFT OUTER JOIN files f " +
            "ON ff.file_idx = f.idx " +
            "WHERE ff.feed_idx = #{idx}")
    List<Files> findByFeedIdx(int idx);

    @Select("<script>" +
            "SELECT * FROM files WHERE idx in (" +
            "<foreach item='item' collection='list' separator=','>" +
            "              #{item}" +
            "</foreach>" +
            ")" +
            "</script>")
    List<Files> findMulti(List<Integer> deleteFileIdx);
}
