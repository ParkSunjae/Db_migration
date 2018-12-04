package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.MemberBlockMember;
import kr.co.kyobo.vora.model.vo.MemberBlockMemberRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberBlockMemberRepository {
    @Select("SELECT" +
            " * " +
            "FROM " +
            "member_block_member " +
            "WHERE o_idx=#{originIdx} " +
            "ORDER BY create_at desc LIMIT #{offset}, #{limit}")
    List<MemberBlockMember> findByOIdx(MemberBlockMemberRequest memberBlockMember);

    @Select("SELECT" +
            "*" +
            "FROM " +
            "member_block_member " +
            "WHERE " +
            "o_idx=#{oIdx} " +
            "and t_idx=#{tIdx} ")
    MemberBlockMember findByOIdxAndTIdx(MemberBlockMember memberBlockMember);

    @Insert("INSERT into member_block_member (o_idx,t_idx) values (#{oIdx},#{tIdx})")
    int insertMemberBlockMember(MemberBlockMember memberBlockMember);

    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "FROM " +
            "member_block_member " +
            "WHERE o_idx=#{originIdx}")
    int countByOIdx(MemberBlockMemberRequest memberBlockMember);
    @Delete("<script>" +
            "DELETE FROM member_block_member " +
            "WHERE " +
            "o_idx=#{oIdx} " +
            "AND t_idx=#{tIdx}" +
            "</script>")
    int deleteByOIdxAndTIdx(MemberBlockMember memberBlockMemberList);
}
