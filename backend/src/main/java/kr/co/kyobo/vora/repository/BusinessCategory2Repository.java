package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.BusinessCategory2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusinessCategory2Repository {
    @Select("SELECT * FROM business_category2 where parent_idx=#{parentIdx}")
    List<BusinessCategory2> findByParentIdx(BusinessCategory2 businessCategory2);
}
