package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class MemberActivity extends BaseObject {
    private Integer memberIdx;
    private String activityComment;
    private String activityType;
    private String ip;
}
