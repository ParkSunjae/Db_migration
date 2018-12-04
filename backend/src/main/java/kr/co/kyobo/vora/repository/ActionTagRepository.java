package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.ActionTags;
import kr.co.kyobo.vora.model.database.Feeds;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActionTagRepository {

    @Insert("INSERT INTO action_tags " +
            "( " +
            " feed_idx , " +
            " action_tag_type ) " +
            "VALUES " +
            "( " +
            "#{feedIdx}, " +
            "#{actionTagType}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    void saveActionTag(ActionTags actionTags);

    @Select("SELECT * FROM action_tags WHERE feed_idx = #{idx}")
    List<ActionTags> findActionTagBookByFeedIdx(Feeds feeds);
}
