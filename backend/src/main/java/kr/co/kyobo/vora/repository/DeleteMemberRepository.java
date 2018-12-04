package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.DeleteMember;
import org.apache.ibatis.annotations.Insert;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeleteMemberRepository {

    @Insert("INSERT INTO delete_member ( idx, file_idx, nick_name, cert_yn, email_cert_date)  VALUES (#{idx}, #{fileIdx}, #{nickName}, #{certYn}, #{emailCertDate})")
    void insertDeleteMember(DeleteMember deleteMember);

}
