package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ios_key_tbl")
public class IosKeyTbl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    @Column(name="hp", columnDefinition = "NVARCHAR(150)")
    private String hp;
    @Column(name="ios_key", columnDefinition = "NVARCHAR(255)")
    private String iosKey;
}
