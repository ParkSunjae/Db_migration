package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedLiker;
import kr.co.kyobo.vora.model.database.Feeds;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedLikerRepository {
    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_liker " +
            "WHERE " +
            " feed_idx = #{idx}")
    List<FeedLiker> findByFeedsIdx(int idx);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "feed_liker " +
            "WHERE " +
            " feed_idx = #{idx} AND u_idx = #{uIdx}")
    FeedLiker findByFeedsIdxAndUidx(Feeds feeds);

    @Insert("INSERT " +
            "INTO " +
            "feed_liker " +
            "( " +
            "feed_idx, " +
            "u_idx " +
            " ) " +
            "VALUES " +
            "(" +
            " #{idx}, " +
            " #{uIdx} " +
            ") ")
    int setTrueLiker(Feeds feeds);

    @Delete("DELETE FROM feed_liker WHERE feed_idx = #{idx} AND u_idx = #{uIdx}")
    int setFalseLiker(Feeds feeds);
}
