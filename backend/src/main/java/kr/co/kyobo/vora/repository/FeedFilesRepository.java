package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedFiles;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface FeedFilesRepository {
    @Insert("<script>" +
            "INSERT INTO feed_files " +
            "( " +
            "feed_idx, " +
            "file_idx " +
            " ) " +
            "VALUES " +

            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "( " +
            " #{item.feedIdx}, " +
            " #{item.fileIdx} " +
            ") " +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    void saveMulti(List<FeedFiles> saveFeedFiles);

    @Select("SELECT f.*  " +
            "FROM feed_files ff " +
            "LEFT OUTER JOIN files f " +
            "ON ff.file_idx = f.idx " +
            "WHERE ff.feed_idx = #{idx} ")
    List<Files> findByFeedIdx(Feeds feeds);

    @Delete("DELETE FROM feed_files WHERE feed_idx = #{idx} ")
    int remove(Files files);

    @Delete("<script>" +
            "DELETE FROM feed_files WHERE feed_idx = #{idx} " +
            "AND file_idx in ( " +
            "<foreach item='item' collection='list' separator=','>" +
            "  #{item,jdbcType=INTEGER}" +
            "</foreach>" +
            " ) " +
            "</script>")
    void removeMulti(HashMap<String, Object> param);

}
