package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class DeleteMember {
    private int idx;
    private int fileIdx;
    private String nickName;
    private String certYn;
    private String emailCertDate;
}
