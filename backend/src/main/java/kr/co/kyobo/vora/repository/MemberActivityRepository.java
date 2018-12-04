package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.api.RequestParam;
import kr.co.kyobo.vora.model.database.MemberActivity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberActivityRepository {

    @Insert("INSERT INTO member_activity " +
            "( " +
            "member_idx, " +
            "activity_comment, " +
            "activity_type, " +
            "ip " +
            " ) " +
            "VALUES " +
            "( " +
            "#{memberIdx}, " +
            "#{activityComment}, " +
            "#{activityType}, " +
            "#{ip} " +
            ") ")
    int saveActivity(MemberActivity memberActivity);

    @Select("<script>" +
            "SELECT SQL_CALC_FOUND_ROWS * FROM member_activity " +
            "WHERE member_idx = #{memberIdx} " +
            "AND  create_at > date_sub(now(), interval 6 month ) " +
            "<if test='startDate!=null'>" +
            "AND create_at > #{startDate} " +
            "</if>" +
            "<if test='endDate!=null'>" +
            "<![CDATA[" +
            "AND create_at <= DATE_SUB(#{endDate}, INTERVAL -1 DAY) " +
            "]]>" +
            "</if>" +
            "ORDER BY idx DESC LIMIT #{offset}, #{limit}" +
            "</script>")
    List<MemberActivity> getActivities(RequestParam requestParam);

    @Select("SELECT FOUND_ROWS()")
    int getTotal();

}
