package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.api.NaverSearchReq;
import kr.co.kyobo.vora.model.database.VoraBooks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoraBooksRespository {
    @Select("SELECT SQL_CALC_FOUND_ROWS" +
            "* " +
            "FROM " +
            "vora_books " +
            "WHERE book_nm LIKE CONCAT('%',#{query},'%') " +
            "LIMIT #{getPage}, #{display}")
    List<VoraBooks> findBooks(NaverSearchReq naverSearchReq);

    @Select("SELECT FOUND_ROWS()")
    int findBooksTotalCount();
}
