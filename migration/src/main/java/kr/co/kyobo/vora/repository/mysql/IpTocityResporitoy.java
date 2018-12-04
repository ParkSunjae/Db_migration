package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.IpToCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IpTocityResporitoy extends JpaRepository<IpToCity, Integer> {

    @Query(value="SELECT idx, ip, city_name, contry_code, contry_name, region_name FROm ip_to_city WHERE ip = ?1", nativeQuery = true)
    IpToCity findByIp(String strip);
}
