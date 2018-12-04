package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Alarm;
import kr.co.kyobo.vora.model.vo.AlarmObj;
import kr.co.kyobo.vora.model.vo.RtnAlarm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlarmRepository {

    @Select("SELECT al.* , fs.file_thumbnail  " +
            "FROM alarm al  " +
            "LEFT OUTER JOIN member mb  " +
            "ON al.u_idx = mb.idx  " +
            "LEFT OUTER JOIN files fs  " +
            "ON mb.file_idx = fs.idx  " +
            "WHERE al.target_idx = #{userIdx} AND al.show_yn = 'N' ORDER BY al.idx DESC")
    List<RtnAlarm> findByUserIdx(int userIdx);

    @Select("SELECT SQL_CALC_FOUND_ROWS al.* , fs.file_thumbnail  " +
            "FROM alarm al  " +
            "LEFT OUTER JOIN member mb  " +
            "ON al.u_idx = mb.idx  " +
            "LEFT OUTER JOIN files fs  " +
            "ON mb.file_idx = fs.idx  " +
            "WHERE al.target_idx = #{uIdx} ORDER BY al.idx DESC " +
            "limit #{offset},#{limit}")
    List<RtnAlarm> findByUserIdxAll(AlarmObj alarmObj);

    @Select("SELECT FOUND_ROWS()")
    int getCount();

    @Select("SELECT count(idx) FROM alarm WHERE target_idx = #{uIdx} AND show_yn = 'N' ORDER BY idx DESC")
    int findByUserIdxForCount(int uIdx);

    @Insert("INSERT INTO alarm " +
            "( " +
            "u_idx, " +
            "target_idx, " +
            "type, " +
            "message, " +
            "show_yn, " +
            "feed_idx" +
            " ) " +
            "VALUES " +
            "( " +
            "#{uIdx}, " +
            "#{targetIdx}, " +
            "#{type}, " +
            "#{message}, " +
            "#{showYn}, " +
            "#{feedIdx}" +
            ") ")
    void save(Alarm alarm);

    @Update("UPDATE alarm SET show_yn = 'Y' WHERE idx = #{idx}")
    int updateShowYnByIdx(Alarm alarm);
}
