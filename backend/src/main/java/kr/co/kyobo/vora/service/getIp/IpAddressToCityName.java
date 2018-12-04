package kr.co.kyobo.vora.service.getIp;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.model.database.IpToCity;
import kr.co.kyobo.vora.repository.IpTocityResporitoy;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class IpAddressToCityName {

    @Value("${ip.to.city.access.key}")
    private String ipCityAccesskey;

    public static final String CALL_CITY_NAME1 = "https://api.ip2location.com/?key=";
    public static final String CALL_CITY_NAME2 = "&package=WS3&format=json&ip=";

    @Autowired
    public IpTocityResporitoy ipTocityResporitoy;

    public String callCityName(HttpServletRequest request) throws IOException {
        String ip = CommonUtils.checkIp(request);

        //check already search ip
        IpToCity finded = this.ipTocityResporitoy.findByIpCity(ip);

        if(finded != null){
            return this.changeCityName(finded.getCityName());
        }else{
            JSONObject json = readJsonFromUrl(CALL_CITY_NAME1 + ipCityAccesskey + CALL_CITY_NAME2 + ip );

            IpToCity saveCity = new IpToCity();
            saveCity.setIp(ip);
            saveCity.setCityName((String) json.get("city_name"));
            saveCity.setContryCode((String) json.get("country_code"));
            saveCity.setContryName((String) json.get("country_name"));
            saveCity.setRegionName((String) json.get("region_name"));
            this.ipTocityResporitoy.insertIptoCity(saveCity);
            return this.changeCityName((String) json.get("city_name"));
        }

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

    private String changeCityName(String cityNameC){
        String cityName = "";
        switch (cityNameC) {
            case "Seoul":
                cityName = "서울";
                break;
            case "Suwon":
                cityName = "수원";
                break;
            case "Incheon":
                cityName = "인천";
                break;
            case "Anyang":
                cityName = "안양";
                break;
            case "Seongnam":
                cityName = "성남";
                break;
            case "Osan":
                cityName = "오산";
                break;
            case "Chuncheon":
                cityName = "춘천";
                break;
            case "Wonju":
                cityName = "원주";
                break;
            case "Cheorwon":
                cityName = "철원";
                break;
            case "Yeongwol":
                cityName = "영월";
                break;
            case "Yangyang":
                cityName = "양양";
                break;
            case "Sokcho":
                cityName = "속초";
                break;
            case "Donghae":
                cityName = "동해";
                break;
            case "Gangneung":
                cityName = "강릉";
                break;
            case "Daegwallyeong":
                cityName = "대관령";
                break;
            case "Chungju":
                cityName = "충주";
                break;
            case "Chupungnyeong":
                cityName = "추풍령";
                break;
            case "Cheonan":
                cityName = "천안";
                break;
            case "Seosan":
                cityName = "서산";
                break;
            case "Daejeon":
                cityName = "대전";
                break;
            case "Geunsan":
                cityName = "금산";
                break;
            case "Jeonju":
                cityName = "전주";
                break;
            case "Iksan":
                cityName = "익산";
                break;
            case "Mokpo":
                cityName = "목포";
                break;
            case "Haenam":
                cityName = "해남";
                break;
            case "Gwangju":
                cityName = "광주";
                break;
            case "Yeosu":
                cityName = "여수";
                break;
            case "Wan-do":
                cityName = "완도";
                break;
            case "Beolgyo":
                cityName = "벌교";
                break;
            case "Gimcheon":
                cityName = "김천";
                break;
            case "Daegu":
                cityName = "대구";
                break;
            case "Ulleung-do":
                cityName = "울릉도";
                break;
            case "Waegwan":
                cityName = "왜관";
                break;
            case "Andong":
                cityName = "안동";
                break;
            case "Pohang":
                cityName = "포항";
                break;
            case "Uiseong":
                cityName = "의성";
                break;
            case "Uljin":
                cityName = "울진";
                break;
            case "Jinhae":
                cityName = "진해";
                break;
            case "Busan":
                cityName = "부산";
                break;
            case "Ulsan":
                cityName = "울산";
                break;
            case "Miryang":
                cityName = "밀양";
                break;
            case "Masan":
                cityName = "마산";
                break;
            case "Changwon":
                cityName = "창원";
                break;
            case "Tongyeong(Chungmu)":
                cityName = "통영(충무)";
                break;
            case "Jinju":
                cityName = "진주";
                break;
            case "Jeju":
                cityName = "제주";
                break;
            case "Bukjeju":
                cityName = "북제주";
                break;
            case "Seogwipo":
                cityName = "서귀포";
                break;
            default:
                cityName = cityNameC;
                break;
        }
        return cityName;
    }


}
