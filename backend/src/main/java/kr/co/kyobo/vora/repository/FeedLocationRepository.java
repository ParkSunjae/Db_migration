package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.FeedLocations;
import kr.co.kyobo.vora.model.database.Feeds;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedLocationRepository {

    @Insert("INSERT INTO feed_locations " +
            "( " +
            " feed_idx , " +
            " address , " +
            " category , " +
            " description , " +
            " link , " +
            " mapx , " +
            " mapy , " +
            " roadAddress , " +
            " title " +
            " ) " +
            "VALUES " +
            "(" +
            " #{feedIdx} , " +
            " #{address} , " +
            " #{category} , " +
            " #{description} , " +
            " #{link} , " +
            " #{mapx} , " +
            " #{mapy} , " +
            " #{roadAddress} , " +
            " #{title} " +
            ")")
    void saveLocation(FeedLocations feedLocations);

    @Select("SELECT * FROM feed_locations WHERE feed_idx = #{idx}")
    FeedLocations findByFeedIdx(Feeds feeds);

    @Delete("DELETE FROM feed_locations WHERE idx = #{deleteLocationIdx}")
    void remove(int deleteLocationIdx);
}
