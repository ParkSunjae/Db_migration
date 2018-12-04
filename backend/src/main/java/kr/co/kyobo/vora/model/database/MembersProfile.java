package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class MembersProfile extends BaseObject {
    private int memberIdx;
    private int fileIdx;
}
