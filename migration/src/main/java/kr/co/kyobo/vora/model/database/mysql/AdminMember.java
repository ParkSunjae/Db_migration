package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class AdminMember extends BaseObject{
    private String adminId;
    private String adminPwd;
    private String adminNickName;
    private String adminGrade;
}
