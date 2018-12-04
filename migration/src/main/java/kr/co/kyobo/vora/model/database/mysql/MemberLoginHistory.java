package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class MemberLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private Integer uIdx;
    private String ip;
    private String location;
    private Date loginTime;
    private String loginType;
    private String logType;
}
