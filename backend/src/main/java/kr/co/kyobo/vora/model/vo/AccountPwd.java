package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.Account;
import lombok.Data;

@Data
public class AccountPwd extends Account {

    private String newPassword;

}
