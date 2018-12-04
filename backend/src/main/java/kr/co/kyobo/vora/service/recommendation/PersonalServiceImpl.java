package kr.co.kyobo.vora.service.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.*;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.MemberObj;
import kr.co.kyobo.vora.model.vo.ResponseFeedComment;
import kr.co.kyobo.vora.model.vo.ResponseMyFeeds;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class PersonalServiceImpl implements PersonalService {
    @Value("${recommendServiceUri}")
    String apiUrl;
    @Value("${recommendTagRelationTag}")
    String recommendTagRelationTag;
    @Value("${recommendPopularPersonPost}")
    String recommendPopularPersonPost;
    @Value("${recommendTagMobileServicePersonalUrl}")
    String recommendTagMobileServicePersonalUrl;
    @Value("${recommendTagPcServicePersonalUrl}")
    String recommendTagPcServicePersonalUrl;
    @Value("${recommendUserMobileServicePersonalUrl}")
    String recommendUserMobileServicePersonalUrl;
    @Value("${recommendUserPcServicePersonalUrl}")
    String recommendUserPcServicePersonalUrl;
    @Value("${recommendPostServicePersonalUrl}")
    String recommendPostServicePersonalUrl;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FeedsRepository feedsRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    FilesRepository filesRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FeedCommentRepository feedCommentRepository;
    @Autowired
    FeedLikerRepository feedLikerRepository;
    @Autowired
    MemberScrapFeedsRepository memberScrapFeedsRepository;
    @Autowired
    FeedMemberTagRespository feedMemberTagRespository;
    @Autowired
    FeedsService feedsService;

    @Override
    public ResponseEntity<Object> tagRecommendMobile(PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendTagMobileServicePersonalUrl, personalRecommendationTag,personalRecommendationTag.getUserId());
    }

    @Override
    public ResponseEntity<Object> tagRecommendPC(PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendTagPcServicePersonalUrl, personalRecommendationTag,personalRecommendationTag.getUserId());
    }

    @Override
    public ResponseEntity<Object> userRecommendMobile(PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendUserMobileServicePersonalUrl, personalRecommendationUser,personalRecommendationUser.getUserId());
    }

    @Override
    public ResponseEntity<Object> userRecommendPC(PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendUserPcServicePersonalUrl, personalRecommendationUser,personalRecommendationUser.getUserId());
    }

    @Override
    public ResponseEntity<Object> postRecommend(PersonalRecommendationPost personalRecommendationPost, HttpServletResponse response) {
        return this.getRecommend(this.apiUrl, this.recommendPostServicePersonalUrl, personalRecommendationPost,personalRecommendationPost.getUserId());
    }

    private ResponseEntity<Object> getRecommend(String apiUrl, String recommendUrl, Object object,int memberIdx){
        RecommendResultData resultData = null;
        try{
            resultData = requestRecommend(apiUrl + recommendUrl,object, RecommendResultData.class);
        }catch(Exception e){
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_FIND_FAIL.getCode());
        }
        return ResponseEntityUtil.makeResultSuccess(loadRealDatas(resultData,recommendUrl,memberIdx));
    }

    private <T> T requestRecommend(String path, Object data, Class<T> type) throws Exception {
        StringBuffer responseString = new StringBuffer();
        /**
         * 추천API에서 데이터 가져오기
         */
        String jsonData = new ObjectMapper().writeValueAsString(data);
        byte[] jsonDataBytes = jsonData.getBytes("UTF-8");
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-Length",String.valueOf(jsonDataBytes.length));
        con.setDoOutput(true);
        con.getOutputStream().write(jsonDataBytes);
        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));;
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

    private Map<String,Object> loadRealDatas(RecommendResultData recommendResultData,String typePath,int filterUser){
        log.info("resultData : "+recommendResultData.toString());
        Map<String,Object> resultDatas = new HashMap<>();
        List<MemberObj> memberList = new ArrayList<>();
        List<Feeds> feedsList = new ArrayList<>();
        List<ResponseMyFeeds> feedsResultList = new ArrayList<>();
        List<Tags> tagsList = new ArrayList<>();
        if(recommendResultData!=null){
            if(recommendResultData.getUsers()!=null && recommendResultData.getUsers().size()>0){
                memberList = memberRepository.findByIdxMulti2(recommendResultData.getUsers());
                for(MemberObj memberObj : memberList){
                    if(memberObj.getFileIdx()>0){
                        memberObj.setProfileInfo(filesRepository.findByIdx(memberObj.getFileIdx()));
                    }
                }
            }else{
                if(typePath.equals(this.recommendUserMobileServicePersonalUrl) ||
                        typePath.equals(this.recommendUserPcServicePersonalUrl)){
                    int limit = 4;
                    if(typePath.equals(this.recommendUserMobileServicePersonalUrl))
                        limit = 6;
                    memberList = memberRepository.findByFollowerTopList(limit);
                    for(MemberObj memberObj : memberList){
                        if(memberObj.getFileIdx()>0){
                            memberObj.setProfileInfo(filesRepository.findByIdx(memberObj.getFileIdx()));
                        }
                    }
                }
            }

            if(recommendResultData.getPosts()!=null && recommendResultData.getPosts().size()>0){
                feedsList = feedsRepository.findFeedsByIdxMulti2(recommendResultData.getPosts());
                FindFeedsListObj findFeedsListObj = new FindFeedsListObj();
                findFeedsListObj.setUIdx(filterUser);
                feedsResultList = feedInfofind(findFeedsListObj,feedsList);
            }else{
            }

            if(recommendResultData.getTags()!=null && recommendResultData.getTags().size()>0){
                tagsList = recommendResultData.getTags();
            }else{
                if(typePath.equals(this.recommendTagMobileServicePersonalUrl) ||
                        typePath.equals(this.recommendTagPcServicePersonalUrl))
                    tagsList = tagsRepository.findTopTagsOnRandom();
            }

            resultDatas.put("memberInfo",memberList);
            resultDatas.put("feedsList",feedsResultList);
            resultDatas.put("tagsList",tagsList);
        }
        return resultDatas;
    }

    private List<ResponseMyFeeds> feedInfofind(FindFeedsListObj feeds, List<Feeds> finded){
        //return object
        List<ResponseMyFeeds> myFeedsArrayList = new ArrayList<>();

        for(Feeds feeds1 : finded){
            ResponseMyFeeds responseMyFeeds = new ResponseMyFeeds();
            BeanUtils.copyProperties(feeds1, responseMyFeeds);

            // 사용자 정보
            Member member = this.memberRepository.findByIdx(feeds1.getUIdx());
            responseMyFeeds.setMember(member);
            Files memberProfile = this.filesRepository.findByIdx(member.getFileIdx());
            responseMyFeeds.setMemberProfile(memberProfile);

            // 계정 정보
            Account account = this.accountRepository.findByUserIdx(member.getIdx());
            responseMyFeeds.setAccount(account);

            // 피드의 코멘트 정보
            List<ResponseFeedComment> responseFeedCommentList = this.feedCommentRepository.findByFeedsIdxOnResonse(feeds1.getIdx());
            for(ResponseFeedComment responseFeedComment : responseFeedCommentList){
                Member member2 = this.memberRepository.findByIdx(responseFeedComment.getUIdx());
                Files memberProfile2 = this.filesRepository.findByIdx(member2.getFileIdx());
                responseFeedComment.setMember(member2);
                responseFeedComment.setFiles(memberProfile2);
            }
            responseMyFeeds.setComments(responseFeedCommentList);
            responseMyFeeds.setCommentCount(responseFeedCommentList.size());

            Boolean meLikeCheck = false;
            Boolean meCommentCheck = false;
            Boolean meScrapCheck = false;
            // 피드의 좋아요 정보
            List<FeedLiker> likers = this.feedLikerRepository.findByFeedsIdx(feeds1.getIdx());
            for(FeedLiker feedLiker : likers){
                if(feedLiker.getUIdx() == feeds.getIdx()){
                    meLikeCheck = true;
                }
            }
            responseMyFeeds.setMeLikeCheck(meLikeCheck);
            responseMyFeeds.setLikers(likers);
            responseMyFeeds.setLikeCount(likers.size());

            // 피드의 스크랍 여부 정보
            List<MemberScrapFeeds> scrapFeeds = this.memberScrapFeedsRepository.findByFeedIdx(feeds1.getIdx());
            for(MemberScrapFeeds memberScrapFeeds : scrapFeeds){
                if(memberScrapFeeds.getUIdx() == feeds.getIdx()){
                    meScrapCheck = true;
                }
            }

            Feeds feeds2 = new Feeds();
            feeds2.setIdx(feeds1.getIdx());
            feeds2.setUIdx(feeds.getUIdx());


            int checkComment = this.feedCommentRepository.findMyCommentByFeedIdxAndUidx(feeds2);
            if(checkComment > 0){
                meCommentCheck = true;
            }
            log.info("meCommentCheck=="+meCommentCheck.toString());
            responseMyFeeds.setMeCommentCheck(meCommentCheck);

            responseMyFeeds.setMeScrapCheck(meScrapCheck);
            responseMyFeeds.setScraps(scrapFeeds);
            responseMyFeeds.setScrapCount(scrapFeeds.size());

            responseMyFeeds.setMeCommentCheck(meCommentCheck);

            // 파일정보
            List<Files> files = this.filesRepository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFiles(files);
            responseMyFeeds.setFileCount(files.size());

            // 피드연관 태그 정보getMyFeedsList
            List<Tags> findTags = this.tagsRepository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFeedTags(findTags);

            // 피드연관 팔로워의 게시물
            List<Member> findFeedMemberTags = this.feedMemberTagRespository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFeedMemberTag(findFeedMemberTags);

            FindFeedsListObj save = new FindFeedsListObj();
            save.setIdx(feeds1.getIdx());
            log.info("feeds==="+feeds1.getIdx());
            List<FeedComment> parents = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByIdxAsc(save);
            log.info("commentSize==="+parents.size());
            responseMyFeeds.setCommentList(this.feedsService.makeCommentList(save, parents, false));
            myFeedsArrayList.add(responseMyFeeds);
        }
        return myFeedsArrayList;
    }
}
