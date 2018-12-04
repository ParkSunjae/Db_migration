package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class IpToCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    private String ip;
    private String cityName;
    private String contryCode;
    private String contryName;
    private String regionName;
//    private String latitude;
//    private String longtitude;
}
