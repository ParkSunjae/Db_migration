package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.MemberFollowMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberFollowMemberRepository {

    @Insert("INSERT INTO member_follow_member (t_idx,o_idx) VALUES" +
            "(" +
            "#{tIdx},#{oIdx}" +
            ")")
    int save(MemberFollowMember memberFollowMember);

    @Insert("<script>" +
            "INSERT IGNORE INTO member_follow_member (t_idx,o_idx) VALUES" +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "(" +
            "#{item.tIdx}," +
            "#{item.oIdx}" +
            ")" +
            "</foreach>" +
            "</script>")
    int saveMulti(List<MemberFollowMember> memberFollowMemberList);

    @Delete("DELETE FROM member_follow_member WHERE t_idx=#{tIdx} AND o_idx=#{oIdx}")
    int deleteMemberFollowMember(MemberFollowMember memberFollowMember);

    @Select("SELECT t_idx FROM member_follow_member WHERE o_idx=#{oIdx}")
    List<MemberFollowMember> findByOIdx(MemberFollowMember memberFollowMember);

    @Select("SELECT o_idx FROM member_follow_member WHERE t_idx=#{tIdx}")
    List<MemberFollowMember> findByTIdx(MemberFollowMember memberFollowMember);

    @Select("SELECT * FROM member_follow_member WHERE t_idx=#{tIdx} AND o_idx=#{oIdx}")
    MemberFollowMember findByOidxAndTIdx(MemberFollowMember memberFollowMember);

    @Select("SELECT " +
            "mo.* " +
            "FROM member mi " +
            "LEFT OUTER JOIN member_follow_member mfm " +
            "ON mi.idx = mfm.o_idx " +
            "LEFT OUTER JOIN member mo " +
            "ON mo.idx = mfm.t_idx " +
            "WHERE mi.idx = #{idx} " +
            "AND mo.nick_name like CONCAT('%',#{nickName},'%') ")
    List<Member> findByTargetNickName(Member member);

    @Select("SELECT COUNT(t_idx) FROM member_follow_member WHERE o_idx=#{oIdx}")
    int countByOIdx(int oIdx);

    @Select("SELECT COUNT(o_idx) FROM member_follow_member WHERE t_idx=#{tIdx}")
    int countByTIdx(int tIdx);

    @Select("SELECT COUNT(idx) FROM member_follow_member WHERE t_idx=#{tIdx} AND  o_idx=#{oIdx}")
    int countByMemberFollowMember(MemberFollowMember memberFollowMember);
}
