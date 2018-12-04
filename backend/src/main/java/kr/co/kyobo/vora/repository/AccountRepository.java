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

    @Insert("Insert into account (user_idx,account_type,email,user_pwd,sns_token,device_type)" +
            "values (#{userIdx},#{accountType},#{email},#{userPwd},#{snsToken},#{deviceType})")
    @Options(useGeneratedKeys = true, keyProperty = "idx")
    int saveAccount(Account account);

    @Select("SELECT " +
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
            "FROM " +
            "account " +
            "WHERE idx = #{idx}")
    Account findByAccountIdx(int idx);

    @Select("SELECT " +
            "account_type, " +
            "email, " +
            "create_at, " +
            "update_at " +
            "FROM " +
            "account " +
            "WHERE user_idx = #{idx} AND account_type='email'")
    Account findByUserIdx(int idx);

    @Update("UPDATE account SET user_pwd = #{userPwd} WHERE idx = #{idx}")
    int updateAccountPwd(Account account);

    @Select("SELECT * FROM account WHERE email = #{email} AND account_type='email'")
    Account findByEmail(String email);

    @Select("SELECT CASE WHEN count(email) > 0 THEN 1 ELSE 0 END  FROM account WHERE email = #{email}")
    int checkDuplicateMail(Account member);
}
