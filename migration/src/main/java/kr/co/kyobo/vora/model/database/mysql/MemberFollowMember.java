package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@IdClass(MemberAndMemberIdx.class)
public class MemberFollowMember{
    @Id
    private Integer oIdx;
    @Id
    private Integer tIdx;
    private Date createAt;
    private Date updateAt;
}
