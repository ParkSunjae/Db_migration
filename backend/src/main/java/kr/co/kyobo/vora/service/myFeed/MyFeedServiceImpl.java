package kr.co.kyobo.vora.service.myFeed;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.*;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MyFeedServiceImpl implements MyFeedService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FeedsRepository feedsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private FeedTagsRepository feedTagsRepository;
    @Autowired
    private FeedFilesRepository feedFilesRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FeedLocationRepository feedLocationRepository;
    @Autowired
    private ActionTagBookRepository actionTagBookRepository;
    @Autowired
    private ActionTagRepository actionTagRepository;
    @Autowired
    private ActionTagMovieRepository actionTagMovieRepository;
    @Autowired
    private ActionTagCultureExhibitionRepository actionTagCultureExhibitionRepository;
    @Autowired
    private FeedMemberTagRespository feedMemberTagRespository;
    @Autowired
    private FeedCommentRepository feedCommentRepository;
    @Autowired
    private FeedLikerRepository feedLikerRepository;
    @Autowired
    private MemberScrapFeedsRepository memberScrapFeedsRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private FeedsService feedsService;


    @Override
    public ResponseEntity<Object> getMyFeedsList(FindFeedsListObj feeds) {
        this.setPage(feeds);
        List<Feeds> finded = this.feedsRepository.findByUIdx(feeds);
        int total = this.feedsRepository.totalCountMyFeed();

        return this.makeReturn(finded, total, feeds);
    }

    private List<ResponseMyFeeds> makeCheckListSize(List<Feeds> finded, FindFeedsListObj feeds) {
        return this.feedInfofind(feeds, finded);
    }


    @Override
    public ResponseEntity<Object> setFeedLikeToggle(Feeds feeds) {
        FeedLiker finded = this.feedLikerRepository.findByFeedsIdxAndUidx(feeds);
        if(finded != null){
            this.feedLikerRepository.setFalseLiker(feeds);
        }else{

            //TODO push

            Feeds findFeed = this.feedsRepository.findFeedsByIdx(feeds);
            Member findMember = this.memberRepository.findByIdx(feeds.getUIdx());

            Alarm alarm = new Alarm();
            alarm.setFeedIdx(feeds.getIdx());
            alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님 피드에 좋아요를 하였습니다.");
            alarm.setUIdx(feeds.getUIdx());
            alarm.setShowYn("N");
            alarm.setTargetIdx(findFeed.getUIdx());
            alarm.setType("l");
            this.saveAlarm(alarm);

            this.feedLikerRepository.setTrueLiker(feeds);
        }
        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> setFeedScrapToggle(Feeds feeds) {
        MemberScrapFeeds finded = this.memberScrapFeedsRepository.FindByFeedIdxAndUIdx(feeds);
        if(finded != null){
            this.memberScrapFeedsRepository.setFalseScrap(feeds);
        }else{

            //TODO push

            Feeds findFeed = this.feedsRepository.findFeedsByIdx(feeds);
            Member findMember = this.memberRepository.findByIdx(feeds.getUIdx());

            Alarm alarm = new Alarm();
            alarm.setFeedIdx(feeds.getIdx());
            alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님 피드를 스크랩 하였습니다.");
            alarm.setUIdx(feeds.getUIdx());
            alarm.setShowYn("N");
            alarm.setTargetIdx(findFeed.getUIdx());
            alarm.setType("l");
            this.saveAlarm(alarm);

            this.memberScrapFeedsRepository.setTrueScrap(feeds);
        }
        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> getMyScrapList(FindFeedsListObj feeds) {
        this.setPage(feeds);
        // 피드정보
        List<Feeds> finded = this.memberScrapFeedsRepository.findByUIdx(feeds);
        int total = this.memberScrapFeedsRepository.totalCountMyScrap();

        return this.makeReturn(finded, total, feeds);
    }

    private ResponseEntity<Object> makeReturn(List<Feeds> finded, int total, FindFeedsListObj feeds) {
        List<ResponseMyFeeds> myFeedsArrayList = new ArrayList<>();
        myFeedsArrayList = this.makeCheckListSize(finded, feeds);
        FinalObj finalObj = this.makeResponseDataSet(feeds, myFeedsArrayList, total);
        finalObj.setRtns(this.makeCommentList(finded, feeds, myFeedsArrayList));
        return ResponseEntityUtil.makeResultSuccess(finalObj);
    }

    private List<ResponseMyFeeds> makeCommentList(List<Feeds> finded, FindFeedsListObj feeds, List<ResponseMyFeeds> myFeedsArrayList) {
        for(ResponseMyFeeds responseMyFeeds : myFeedsArrayList){
            FindFeedsListObj save = new FindFeedsListObj();
            save.setIdx(responseMyFeeds.getIdx());
            List<FeedComment> parents = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByIdxAsc(save);
            responseMyFeeds.setCommentList(this.feedsService.makeCommentList(feeds, parents, false));
        }
        return myFeedsArrayList;
    }


    @Override
    public ResponseEntity<Object> getAtMemberList(FindFeedsListObj feeds) {
        this.setPage(feeds);
        // 피드정보
        List<Feeds> finded = this.feedMemberTagRespository.findByUIdx(feeds);
        int total = this.feedMemberTagRespository.totalCountTogether();
        return this.makeReturn(finded, total, feeds);
    }

    private void setPage(FindFeedsListObj feeds) {
        if(feeds.getPage() > 1){
            feeds.setOffset((feeds.getPage() - 1) * feeds.getLimit());
        }else{
            feeds.setOffset((feeds.getPage() - 1));
        }
    }
    private void saveAlarm(Alarm alarm){
        this.alarmRepository.save(alarm);
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
            List<ResponseFeedComment> comments = this.feedCommentRepository.findByFeedsIdxOnResonse(feeds1.getIdx());
            responseMyFeeds.setComments(comments);
            responseMyFeeds.setCommentCount(comments.size());

            Boolean meLikeCheck = false;
            Boolean meCommentCheck = false;
            Boolean meScrapCheck = false;
            // 피드의 좋아요 정보
            List<FeedLiker> likers = this.feedLikerRepository.findByFeedsIdx(feeds1.getIdx());
            for(FeedLiker feedLiker : likers){
                if(feedLiker.getUIdx() == feeds.getUIdx()){
                    meLikeCheck = true;
                }
            }
            responseMyFeeds.setMeLikeCheck(meLikeCheck);
            responseMyFeeds.setLikers(likers);
            responseMyFeeds.setLikeCount(likers.size());

            // 피드의 스크랍 여부 정보
            List<MemberScrapFeeds> scrapFeeds = this.memberScrapFeedsRepository.findByFeedIdx(feeds1.getIdx());
            for(MemberScrapFeeds memberScrapFeeds : scrapFeeds){
                if(memberScrapFeeds.getUIdx() == feeds.getUIdx()){
                    meScrapCheck = true;
                }
            }

            responseMyFeeds.setMeScrapCheck(meScrapCheck);
            responseMyFeeds.setScraps(scrapFeeds);
            responseMyFeeds.setScrapCount(scrapFeeds.size());

            responseMyFeeds.setMeCommentCheck(meCommentCheck);

            // 파일정보
            List<Files> files = this.filesRepository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFiles(files);
            responseMyFeeds.setFileCount(files.size());

            // 피드연관 태그 정보
            List<Tags> findTags = this.tagsRepository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFeedTags(findTags);

            // 피드연관 팔로워의 게시물
            List<Member> findFeedMemberTags = this.feedMemberTagRespository.findByFeedIdx(feeds1.getIdx());
            responseMyFeeds.setFeedMemberTag(findFeedMemberTags);
            myFeedsArrayList.add(responseMyFeeds);
        }
        return myFeedsArrayList;
    }
}
