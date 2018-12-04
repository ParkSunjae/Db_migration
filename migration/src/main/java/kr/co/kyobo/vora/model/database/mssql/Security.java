package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "security")
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    private String strUid;
    private String strIp;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strContent;
    private Date inDate;
}
