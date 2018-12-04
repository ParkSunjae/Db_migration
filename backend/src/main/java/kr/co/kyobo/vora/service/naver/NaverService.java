package kr.co.kyobo.vora.service.naver;

import kr.co.kyobo.vora.model.api.NaverLocationSearchReq;
import kr.co.kyobo.vora.model.api.NaverSearchReq;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

@Slf4j
@Service
public class NaverService {

    private static final String NAVER_CLIENT_ID ="QGNSU_ILnK1nQkBc7JbJ";
    private static final String NAVER_SECRET ="Cz68Vb5EEK";


    public ResponseEntity<Object> searchMovie(NaverSearchReq req){
        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("page", req.getStart());
        returnObj.put("pagePerCount", req.getDisplay());
        StringBuffer response = new StringBuffer();
        try {
            String apiURL = this.getMovieUrl(req); // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", NAVER_SECRET);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            log.info("response json String : \n" + response.toString());

            returnObj.put("naverRes", response.toString());

        }catch (IOException e){
            e.printStackTrace();
        }

        return ResponseEntityUtil.makeResultSuccess(returnObj);
    }

    private String getMovieUrl(NaverSearchReq req) throws UnsupportedEncodingException {
        String url = "https://openapi.naver.com/v1/search/movie.json?" + "query=" +  URLEncoder.encode(req.getQuery(), "UTF-8");

        if( req.getGenre() != null ){
            url += "&genre=" + req.getGenre();
        }
        if( req.getDisplay() != null ){
            url += "&display=" + req.getDisplay();
        }
        if( req.getStart() != null ){
            if(req.getStart() != 1){
                int count = (req.getStart() -1)* req.getDisplay() + 1;
                log.info("movie count ===="+ count);
                url += "&start=" + count;
            }else{
                url += "&start=" + req.getStart();
            }
        }
        if( req.getYearfrom() != null ){
            url += "&yearfrom=" + req.getYearfrom();
        }
        if( req.getYearto() != null ){
            url += "&yearto=" + req.getYearto();
        }
        if( req.getCountry() != null ){
            url += "&country=" + req.getCountry();
        }

        return url;
    }

    public ResponseEntity<Object> searchLocation(NaverLocationSearchReq req) {

        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("page", req.getStart());
        returnObj.put("pagePerCount", req.getDisplay());

        StringBuffer response = new StringBuffer();
        try {
            String apiURL = getLocationUrl(req); // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", NAVER_SECRET);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            log.info("response json String : \n" + response.toString());

        }catch (IOException e){
            e.printStackTrace();
        }

        returnObj.put("naverRes", response.toString());

        return ResponseEntityUtil.makeResultSuccess(returnObj);
    }


    private String getLocationUrl(NaverLocationSearchReq req) throws UnsupportedEncodingException {
        String url = "https://openapi.naver.com/v1/search/local.json" + "?query=" +  URLEncoder.encode(req.getQuery(), "UTF-8");

        if( req.getSort() != null ){
            url += "&sort=" + req.getSort();
        }
        if( req.getDisplay() != null ){
            url += "&display=" + req.getDisplay();
        }
        if( req.getStart() != null ){
            if(req.getStart() != 1){
                url += "&start=" + (req.getStart() -1)* req.getDisplay() + 1;
            }else{
                url += "&start=" + req.getStart();
            }

        }

        return url;
    }

}
