package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.api.RequestParam;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberLoginHistoryRepository {

    @Insert("INSERT INTO member_login_history(" +
            "u_idx,ip, location, login_type, log_type, login_time) values" +
            "(#{uIdx},#{ip},#{location},#{loginType},#{logType},now())")
    int saveLoginHistory(MemberLoginHistory memberLoginHistory);

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
    List<MemberLoginHistory> getLoginHistory(RequestParam requestParam);

    @Select("SELECT FOUND_ROWS()")
    int getTotal();

    @Select("SELECT idx, u_idx, ip, location, login_type, login_time FROM member_login_history WHERE u_idx = #{userIdx} ORDER BY idx DESC limit 1")
    MemberLoginHistory findLastHistoryByMemberIdx(int userIdx);

    @Select("SELECT CASE WHEN count(idx) > 0 THEN 1 ELSE 0 END  FROM vora_db.member_login_history WHERE u_idx = #{idx} AND date_format(login_time,'%Y-%m-%d')  = date_format(now(),'%Y-%m-%d')")
    int todayLoginChecker(int idx);
}
