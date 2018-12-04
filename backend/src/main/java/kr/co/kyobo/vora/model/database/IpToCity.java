package kr.co.kyobo.vora.model.database;

import lombok.Data;

@Data
public class IpToCity {
    private int idx;
    private String ip;
    private String cityName;
    private String contryCode;
    private String contryName;
    private String regionName;
    private String latitude;
    private String longtitude;
}
