package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "member_admin_recommend")
public class MemberAdminRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private Integer uIdx;
    private Date startDate;
    private Date endDate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String contents;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createAt;
    private Integer count;
}
