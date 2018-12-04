package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Account extends BaseObject{
    private Integer userIdx;
    private String accountType;
    private String email;
    private String userPwd;
    private String snsToken;
    private String deviceType;
    private String pushToken;
    private String pushYn;
}
