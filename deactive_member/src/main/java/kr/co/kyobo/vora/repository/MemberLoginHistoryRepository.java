package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface MemberLoginHistoryRepository {

    @Select("<script>" +
            "SELECT SQL_CALC_FOUND_ROWS * FROM member_login_history " +
            "WHERE u_idx=#{memberIdx} " +
            "AND login_time > date_sub(now(), interval 90 day) " +
            "<if test='startDate!=null'>" +
            "AND login_time > #{startDate} " +
            "</if>" +
            "<if test='endDate!=null'>" +
            "<![CDATA[" +
            "AND login_time <= DATE_SUB(#{endDate}, INTERVAL -1 DAY) " +
            "]]>" +
            "</if>" +
            "ORDER BY idx DESC limit #{offset}, #{limit}" +
            "</script>")
    List<MemberLoginHistory> getLoginHistory(MemberLoginHistory requestParam);

    @Select("SELECT idx, u_idx, ip,location, login_type, max(login_time) as login_time , log_type, NOW() as today  FROM member_login_history WHERE u_idx in (SELECT idx FROM member WHERE user_status NOT IN ('Deactive', 'EXIT')) GROUP BY u_idx ORDER BY login_time DESC")
    List<MemberLoginHistory> findByMembers();
}
