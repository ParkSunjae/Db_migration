package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberRepository {
    @Select("SELECT * FROM member WHERE user_status NOT IN ('DEACTIVE', 'EXIT')")
    List<Member> getMembersByStatus();

    @Select("<script>" +
            "UPDATE member SET user_status = 'DEACTIVE' WHERE idx in (" +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "#{item.uIdx}" +
            "</foreach> ) " +
            "</script> " )
    void changeMemberDeActive(List<MemberLoginHistory> changeStatuses);


    @Select("SELECT * FROM member WHERE idx = #{uIdx}")
    Member findByMemberIdx(int uIdx);
}