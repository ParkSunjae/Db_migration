package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "login_history")
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private String strid;
    private String strip;
    private String strcountry;
    private String strdevice;
    private String regdate;
}
