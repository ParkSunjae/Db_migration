package kr.co.kyobo.vora.service.ip;

import java.io.IOException;

public interface IpService {
    void setIpToCity() throws IOException;

    void callCityName(String ip) throws IOException ;
    String callCityNameRtn(String ip) throws IOException ;
}
