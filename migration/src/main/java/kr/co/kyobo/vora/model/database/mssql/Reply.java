package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "Reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_no")
    private Integer rNo;
    @Column(name = "b_idx")
    private String bIdx;
    private String userId;
    private String userName;
    @Column(name = "r_icon", columnDefinition = "CHAR(1)")
    private String rIcon;
    @Lob
    @Column(name = "r_text",columnDefinition = "TEXT")
    private String rText;
    @Column(name = "r_date")
    private Date rDate;
}
