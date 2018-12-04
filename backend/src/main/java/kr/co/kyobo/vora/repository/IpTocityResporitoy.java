package kr.co.kyobo.vora.repository;

import kr.co.kyobo.vora.model.database.IpToCity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IpTocityResporitoy {

    @Insert("INSERT INTO ip_to_city " +
            "(ip, " +
            "city_name, " +
            "contry_code, " +
            "contry_name, " +
            "region_name) " +
            "VALUES " +
            "(#{ip}, " +
            "#{cityName}, " +
            "#{contryCode}, " +
            "#{contryName}, " +
            "#{regionName}) ")
    void insertIptoCity(IpToCity ipToCity);

    @Select("SELECT  idx , " +
            "     ip , " +
            "     city_name , " +
            "     contry_code , " +
            "     contry_name , " +
            "     region_name " +
            "FROM ip_to_city WHERE ip = #{ip}")
    IpToCity findByIpCity(String ip);

}
