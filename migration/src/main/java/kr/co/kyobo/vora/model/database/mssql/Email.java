package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intSeq;
    @Column(columnDefinition = "CHAR(1)")
    private String mGubun;
    private Integer memcount;
    private String strSubject;
    @Lob
    @Column(name="strMailMemo",columnDefinition = "TEXT")
    private String strMailMemo;
    private String strId;
    private Date indate;
}
