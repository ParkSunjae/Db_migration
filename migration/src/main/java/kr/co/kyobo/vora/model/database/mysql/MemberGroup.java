package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class MemberGroup extends BaseObject{
    private String groupName;
    private String groupType;
    private String useYn;

}
