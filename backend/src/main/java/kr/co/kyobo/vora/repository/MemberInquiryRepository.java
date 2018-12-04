package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.MemberInquiry;
import kr.co.kyobo.vora.model.vo.MemberInquiryRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberInquiryRepository {
    @Select("SELECT * FROM member_inquiry" +
            "ORDER BY create_at DESC LIMIT #{offset}, #{limit}")
    List<MemberInquiry> findByReqeust(MemberInquiryRequest memberInquiryRequest);

    @Select("SELECT " +
            "CASE WHEN count(idx) > 0 THEN count(idx) ELSE 0 END " +
            "from member_inquiry")
    int countAll(MemberInquiryRequest memberInquiryRequest);

    @Insert("INSERT INTO member_inquiry (" +
            "u_idx, type, content, re_email" +
            ") VALUES(" +
            "#{uIdx},#{type},#{content},#{reEmail}" +
            ")")
    int saveInquiry(MemberInquiry memberInquiry);

    @Update("<script>" +
            "update member_inquiry " +
            "<set>" +
            "<if test=\"type!=null\">type=#{type},</if>" +
            "<if test=\"content!=null\">content=#{content},</if>" +
            "<if test=\"reEmail\">re_email=#{reEmail},</if>" +
            "<if test=\"status\">status=#{status},</if>" +
            "<if test=\"reContent\">re_content=#{reContent},</if>" +
            "</set>" +
            "</script>")
    int updateInquiry(MemberInquiry memberInquiry);
}
