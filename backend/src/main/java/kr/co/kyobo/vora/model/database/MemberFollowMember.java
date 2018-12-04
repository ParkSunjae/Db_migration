package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MemberFollowMember extends BaseObject {
    private int oIdx;
    private int tIdx;
}
