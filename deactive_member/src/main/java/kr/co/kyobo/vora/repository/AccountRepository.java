package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountRepository {
    @Select("select " +
            "idx, " +
            "user_idx, " +
            "account_type, " +
            "device_type, " +
            "push_token, " +
            "push_yn, " +
            "email, " +
            "user_pwd, " +
            "sns_token, " +
            "create_at, " +
            "update_at " +
            "FROM account " +
            "WHERE account_type='email' " +
            "AND email=#{email} " +
            "AND user_pwd=#{userPwd}" +
            "")
    Account findByEmailAndPassword(Account account);


    @Select("SELECT * FROM account WHERE user_idx = #{uIdx}")
    Account findByMemberIdx(int uIdx);
}
