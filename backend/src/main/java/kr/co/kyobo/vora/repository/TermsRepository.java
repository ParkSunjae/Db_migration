package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Terms;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TermsRepository {

    @Select("(SELECT * FROM terms WHERE type = 't' order by idx desc limit 1) " +
            "union " +
            "(SELECT * FROM terms WHERE type = 'p' order by idx desc limit 1)")
    List<Terms> findJoinTerms();
    @Select("SELECT * FROM terms " +
            "WHERE type = #{type} ")
    List<Terms> findAllTerms(Terms terms);
}
