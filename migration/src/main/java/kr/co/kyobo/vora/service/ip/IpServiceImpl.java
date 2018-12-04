package kr.co.kyobo.vora.service.ip;

import kr.co.kyobo.vora.model.database.mssql.LoginHistory;
import kr.co.kyobo.vora.model.database.mysql.IpToCity;
import kr.co.kyobo.vora.repository.mssql.LoginHistoryMssqlRepository;
import kr.co.kyobo.vora.repository.mysql.IpTocityResporitoy;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Service
@Transactional
public class IpServiceImpl implements IpService {

    @Autowired
    private IpTocityResporitoy ipTocityResporitoy;

    @Autowired
    private LoginHistoryMssqlRepository loginHistoryMssqlRepository;

    private String ipCityAccesskey = "A19646B09C";

    public static final String CALL_CITY_NAME1 = "https://api.ip2location.com/?key=A19646B09C&package=WS3&format=json&ip=";

    @Override
    public void callCityName(String ip) throws IOException {

        //check already search ip
        JSONObject json = readJsonFromUrl(CALL_CITY_NAME1+ ip );

        IpToCity saveCity = new IpToCity();
        saveCity.setIp(ip);
        saveCity.setCityName((String) json.get("city_name"));
        saveCity.setContryCode((String) json.get("country_code"));
        saveCity.setContryName((String) json.get("country_name"));
        saveCity.setRegionName((String) json.get("region_name"));
        this.ipTocityResporitoy.save(saveCity);

    }

    @Override
    public String callCityNameRtn(String ip) throws IOException {

        //check already search ip
        JSONObject json = readJsonFromUrl(CALL_CITY_NAME1+ ip );

        IpToCity saveCity = new IpToCity();
        saveCity.setIp(ip);
        saveCity.setCityName((String) json.get("city_name"));
        saveCity.setContryCode((String) json.get("country_code"));
        saveCity.setContryName((String) json.get("country_name"));
        saveCity.setRegionName((String) json.get("region_name"));
        this.ipTocityResporitoy.save(saveCity);
        return (String) json.get("city_name");
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @Override
    public void setIpToCity() throws IOException {
        Iterable<String> getAll = this.loginHistoryMssqlRepository.findDistinctAll();

        for(String ip : getAll){
            this.callCityName(ip);
        }

    }
}
