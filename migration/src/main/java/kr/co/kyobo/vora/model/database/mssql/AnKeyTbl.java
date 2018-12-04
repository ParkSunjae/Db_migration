package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "an_key_tbl")
public class AnKeyTbl{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    private String hp;
    @Column(name="an_key")
    private String anKey;
}
