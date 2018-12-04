package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="member_terms_apply")
public class MemberTermsApply extends BaseObject{
    private Integer uIdx;
    private Integer termIdx;
    private String checkYn;
}
