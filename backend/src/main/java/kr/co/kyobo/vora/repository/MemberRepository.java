package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.vo.MemberObj;
import kr.co.kyobo.vora.model.vo.SuggestMember;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberRepository {

    @Select("" +
            "")
    Member findByUserEmailAndPassword(Member login);

    @Update("")
    int updateMemberLogin(Member user);

    @Insert("insert into member (nick_name) values (#{nickName})")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    void saveMember(Member member);

    @Select("Select " +
            "CASE WHEN count(nick_name) > 0 THEN 1 ELSE 0 END " +
            "FROM " +
            "member " +
            "WHERE " +
            "nick_name = #{nickName}")
    int checkNickName(Member member);

    @Select("SELECT idx, nick_name, content, year, file_idx, " +
            "email_cert_yn, email_cert_date," +
            "business_yn, business_name, business_content, " +
            "business_category_idx, business_sub_category_idx, " +
            "business_email, business_tel, business_site, " +
            "business_sdate, business_edate, create_at, update_at " +
            "FROM member WHERE idx=#{idx}")
    Member findByIdx(int idx);

    @Select("<script>" +
            "SELECT " +
            "idx, nick_name, business_yn " +
            "FROM member " +
            "where idx in " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item.idx}" +
            "</foreach>" +
            "</script>")
    List<Member> findByIdxMulti(List<Member> memberList);

    @Select("<script>" +
            "SELECT " +
            "idx, nick_name, business_yn, file_idx " +
            "FROM member " +
            "where idx in " +
            "<foreach item='item' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<MemberObj> findByIdxMulti2(List<Integer> memberList);

    @Update("<script>" +
            "UPDATE member " +
            "<set>" +
            "<if test=\"nickName!=null\">nick_name=#{nickName},</if>" +
            "<if test=\"fileIdx!=null\">file_idx=#{fileIdx},</if>" +
            "<if test=\"content!=null\">content=#{content},</if>" +
            "<if test=\"businessYn\">business_yn=#{businessYn},</if>" +
            "<if test=\"businessName\">business_name=#{businessName},</if>" +
            "<if test=\"businessContent\">business_content=#{businessContent},</if>" +
            "<if test=\"businessCategoryIdx\">business_category_idx=#{businessCategoryIdx},</if>" +
            "<if test=\"businessSubCategoryIdx\">business_sub_category_idx=#{businessSubCategoryIdx},</if>" +
            "<if test=\"businessEmail\">business_email=#{businessEmail},</if>" +
            "<if test=\"businessTel\">business_tel=#{businessTel},</if>" +
            "<if test=\"businessSite\">business_site=#{businessSite},</if>" +
            "idx=idx" +
            "</set>" +
            "WHERE idx=#{idx}" +
            "</script>")
    int modifyMemberInfo(Member member);

    @Update("UPDATE member " +
            "SET " +
            "year = #{year}" +
            "WHERE idx=#{idx}")
    int saveMemberYear(Member member);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "member " +
            "WHERE " +
            "nick_name like CONCAT('%',#{nickName},'%')")
    List<Member> findByNickName(Member member);

    @Select("SELECT " +
            " m.idx, " +
            " m.group_idx, " +
            " m.file_idx, " +
            " m.nick_name, " +
            " m.business_yn, " +
            " m.business_name, " +
            " m.business_content, " +
            " bc1.category_name as category_name_parent, " +
            " bc2.category_name as category_name_child, " +
            " m.business_email, " +
            " m.business_tel, " +
            " m.business_site, " +
            " m.business_sdate, " +
            " m.business_edate, " +
            " m.email_cert_yn, " +
            " m.user_status, " +
            " m.create_at, " +
            " m.update_at, " +
            " m.year, " +
            " m.content, " +
            " m.admin_check_yn," +
            " (SELECT COUNT(idx) FROM member_follow_member WHERE o_idx = m.idx) AS follower_count " +
            " FROM member m " +
            "LEFT OUTER JOIN business_category1 bc1 " +
            "ON bc1.idx = m.business_category_idx " +
            "LEFT OUTER JOIN business_category2 bc2 " +
            "ON bc2.idx = m.business_sub_category_idx " +
            "WHERE " +
            "m.admin_check_yn = 'Y' ORDER BY rand() limit 50")
    List<SuggestMember> findByAdminCheckYn();

    @Select("<script>" +
            "SELECT  " +
            " m.idx, " +
            " m.group_idx, " +
            " m.file_idx, " +
            " m.nick_name, " +
            " m.business_yn, " +
            " m.business_name, " +
            " m.business_content, " +
            " bc1.category_name as category_name_parent, " +
            " bc2.category_name as category_name_child, " +
            " m.business_email, " +
            " m.business_tel, " +
            " m.business_site, " +
            " m.business_sdate, " +
            " m.business_edate, " +
            " m.email_cert_yn, " +
            " m.user_status, " +
            " m.create_at, " +
            " m.update_at, " +
            " m.year, " +
            " m.content, " +
            " m.admin_check_yn," +
            " (SELECT COUNT(idx) FROM member_follow_member WHERE o_idx = m.idx) AS follower_count " +
            "FROM member m " +
            "LEFT OUTER JOIN business_category1 bc1 " +
            "ON bc1.idx = m.business_category_idx " +
            "LEFT OUTER JOIN business_category2 bc2 " +
            "ON bc2.idx = m.business_sub_category_idx " +
            "WHERE m.idx in ( " +
            "<foreach item='item' collection='list' open='' separator=',' close=''>" +
            "#{item}" +
            "</foreach>" +
            ")" +
            "</script>")
    List<SuggestMember> findBySuggestUserIdxs(List<Integer> users);

    @Select("SELECT m.* from " +
            "(SELECT user_id as idx FROM view_follower_users ORDER BY cnt desc LIMIT 100) a " +
            "LEFT JOIN member m ON a.idx = m.idx " +
            "LIMIT #{limit} ")
    List<MemberObj> findByFollowerTopList(int limit);

    @Update("UPDATE member SET email_cert_yn = 'Y', email_cert_date = NOW(), user_status = 'ACTIVE' WHERE idx = #{memberIdx}")
    void changeCertMail(int memberIdx);

    @Update("UPDATE member SET user_status = 'ACTIVE' WHERE idx = #{memberIdx}")
    void changeUserStatus(int memberIdx);

    @Delete("DELETE FROM member WHERE idx = #{idx}")
    void exitMember(Member member);
}