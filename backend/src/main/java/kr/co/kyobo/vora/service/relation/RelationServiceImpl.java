package kr.co.kyobo.vora.service.relation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.*;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Tags;
import kr.co.kyobo.vora.model.vo.MemberObj;
import kr.co.kyobo.vora.model.vo.SuggestMember;
import kr.co.kyobo.vora.repository.FeedsRepository;
import kr.co.kyobo.vora.repository.FilesRepository;
import kr.co.kyobo.vora.repository.MemberRepository;
import kr.co.kyobo.vora.repository.TagsRepository;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class RelationServiceImpl implements RelationService {
    @Value("${recommendServiceUri}")
    String apiUrl;
    @Value("${recommendTagRelationTag}")
    String recommendTagRelationTag;
    @Value("${recommendTagRelationUser}")
    String recommendTagRelationUser;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FilesRepository filesRepository;

    @Override
    public ResponseEntity<Object> tagRelationTag(Tags tags, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendTagRelationTag, tags);
    }

    @Override
    public ResponseEntity<Object> tagRelationUser(PersonagesRelatedToEachTagRequest personagesRelatedToEachTagRequest, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendTagRelationUser, personagesRelatedToEachTagRequest);
    }

    private ResponseEntity<Object> getRecommend(String apiUrl, String recommendUrl, Object object){
        RecommendResultData resultData = null;
        try{
            resultData = requestRecommend(apiUrl + recommendUrl,object, RecommendResultData.class);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_FIND_FAIL.getCode());
        }
        return ResponseEntityUtil.makeResultSuccess(loadRealDatas(resultData));
    }

    private <T> T requestRecommend(String path, Object data, Class<T> type) throws Exception {
        StringBuffer responseString = new StringBuffer();
        /**
         * 추천API에서 데이터 가져오기
         */
        String jsonData = new ObjectMapper().writeValueAsString(data);
        log.info("jsonData : "+jsonData);
        byte[] jsonDataBytes = jsonData.getBytes("UTF-8");
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-Length",String.valueOf(jsonDataBytes.length));
        con.setDoOutput(true);
        con.getOutputStream().write(jsonDataBytes);
        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            responseString.append(inputLine);
        }
        br.close();
        con.disconnect();
        /**
         * 추천 데이터 파싱
         */
        return new ObjectMapper().readValue(responseString.toString(),type);
    }

    private Map<String,Object> loadRealDatas(RecommendResultData recommendResultData){
        log.info("resultData : "+recommendResultData.toString());
        Map<String,Object> resultDatas = new HashMap<>();
        List<SuggestMember> memberList = new ArrayList<>();
        List<Tags> tagsList = new ArrayList<>();
        if(recommendResultData!=null){
            if(recommendResultData.getUsers()!=null && recommendResultData.getUsers().size()>0){
                memberList = memberRepository.findBySuggestUserIdxs(recommendResultData.getUsers());
                for(SuggestMember memberObj : memberList){
                    if(memberObj.getFileIdx()>0){
                        memberObj.setProfileInfo(filesRepository.findByIdx(memberObj.getFileIdx()));
                    }
                }
            }
            if(recommendResultData.getTags()!=null && recommendResultData.getTags().size()>0){
                tagsList = recommendResultData.getTags();
            }
            resultDatas.put("memberInfo",memberList);
            resultDatas.put("tagsList",tagsList);
        }
        return resultDatas;
    }
}
