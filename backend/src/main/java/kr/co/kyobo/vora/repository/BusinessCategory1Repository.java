package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.BusinessCategory1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusinessCategory1Repository {
    @Select("SELECT * FROM business_category1")
    List<BusinessCategory1> findAll();
}
