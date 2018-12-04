package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberGroup extends BaseObject{
    private String groupName;
    private String groupType;
    private String useYn;

}
