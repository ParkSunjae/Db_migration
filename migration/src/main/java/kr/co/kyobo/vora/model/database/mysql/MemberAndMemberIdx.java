package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MemberAndMemberIdx implements Serializable {
    private Integer oIdx;
    private Integer tIdx;
}
