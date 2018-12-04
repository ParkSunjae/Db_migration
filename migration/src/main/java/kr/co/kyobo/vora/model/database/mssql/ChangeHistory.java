package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "change_history")
public class ChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Idx;
    private String regdate;
    private String strip;
    private String change1;
    private String strid;
}
