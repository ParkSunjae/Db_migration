package kr.co.kyobo.vora.service.searchFeed;

import kr.co.kyobo.vora.common.CommonUtils;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.FinalObj;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.model.vo.ResponseFeedComment;
import kr.co.kyobo.vora.model.vo.ResponseMyFeeds;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SearchFeedServiceImpl implements SearchFeedService {
    @Autowired
    private FeedsRepository feedsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FeedLikerRepository feedLikerRepository;
    @Autowired
    private FeedCommentRepository feedCommentRepository;
    @Autowired
    private MemberScrapFeedsRepository memberScrapFeedsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private FeedMemberTagRespository feedMemberTagRespository;
    @Autowired
    private FeedsService feedsService;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Override
    public ResponseEntity<Object> searchFeedList(FindFeedsListObj findFeedsListObj, HttpServletRequest request) {
        setPage(findFeedsListObj);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setIp(CommonUtils.checkIp(request));
        searchHistory.setSearch(findFeedsListObj.getQuery());
        searchHistory.setUIdx(findFeedsListObj.getIdx());
        searchHistoryRepository.saveQueryHistory(searchHistory);

        List<Feeds> feedList = feedsRepository.findByQuery(findFeedsListObj);
        int total = feedsRepository.totalCountMyFeed();
        List<ResponseMyFeeds> searchResult = feedInfofind(findFeedsListObj,feedList);
        FinalObj finalObj = makeResponseDataSet(findFeedsListObj,searchResult,total);
        return ResponseEntityUtil.makeResultSuccess(finalObj);
    }

    private FinalObj makeResponseDataSet(FindFeedsListObj feeds, List<ResponseMyFeeds> myFeedsArrayList, int total) {
        FinalObj finalObj = new FinalObj();
        finalObj.setLimit(feeds.getLimit());
        if(myFeedsArrayList.size() > 0){
            finalObj.setPage(feeds.getPage() + 1);
        }else{
            finalObj.setPage(feeds.getPage());
        }

        finalObj.setTotalCount(total);



        for(ResponseMyFeeds responseMyFeeds : myFeedsArrayList){
            FindFeedsListObj save = new FindFeedsListObj();
            save.setIdx(responseMyFeeds.getIdx());
            List<FeedComment> parents = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByIdxAsc(save);
            responseMyFeeds.setCommentList(this.feedsService.makeCommentList(feeds, parents, false));
        }
        finalObj.setRtns(myFeedsArrayList);
        return finalObj;
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

    private void setPage(FindFeedsListObj findFeedsListObj) {
        if (findFeedsListObj.getPage() > 1) {
            findFeedsListObj.setOffset((findFeedsListObj.getPage() - 1) * findFeedsListObj.getLimit());
        } else {
            findFeedsListObj.setOffset((findFeedsListObj.getPage() - 1));
        }
    }
}
