package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "push")
public class Push {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    @Column(name = "business_group")
    private String businessGroup;
    private String platform;
    private String sendtype;
    private String senddate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String strcontent;
    private Integer sendea;
    private String struid;
    private Date indate;
}
