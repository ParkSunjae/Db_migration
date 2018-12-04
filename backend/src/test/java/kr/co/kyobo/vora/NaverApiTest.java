package kr.co.kyobo.vora;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@SpringBootTest
@Slf4j
public class NaverApiTest {

//    @Test
//    public void NaverMovieApiTest() throws Exception {
//         String naver_client_id ="QGNSU_ILnK1nQkBc7JbJ";
//         String naver_secret ="Cz68Vb5EEK";
//
//        String url2 = "https://openapi.naver.com/v1/search/movie.json?query=" + URLEncoder.encode("서울", "UTF-8");
//
//        log.info("sendUrl===" + url2);
//
//        StringBuffer response = new StringBuffer();
//        URL url = new URL(url2);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("X-Naver-Client-Id", naver_client_id);
//        con.setRequestProperty("X-Naver-Client-Secret", naver_secret);
//        int responseCode = con.getResponseCode();
//        BufferedReader br;
//        if (responseCode == 200) { // 정상 호출
//            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        } else {  // 에러 발생
//            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//        }
//        String inputLine;
//
//        while ((inputLine = br.readLine()) != null) {
//            response.append(inputLine);
//        }
//        br.close();
//        log.info("response json String : \n" + response.toString());
//    }

}
