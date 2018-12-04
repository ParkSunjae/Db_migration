package kr.co.kyobo.vora.service.memberFeed;

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
public class MemberFeedServiceImpl implements MemberFeedService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FeedsRepository feedsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FeedLocationRepository feedLocationRepository;
    @Autowired
    private FeedMemberTagRespository feedMemberTagRespository;
    @Autowired
    private FeedCommentRepository feedCommentRepository;
    @Autowired
    private FeedLikerRepository feedLikerRepository;
    @Autowired
    private MemberScrapFeedsRepository memberScrapFeedsRepository;
    @Autowired
    private TagFollowRepository tagFollowRepository;
    @Autowired
    private FeedTagsRepository feedTagsRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private MemberFollowMemberRepository memberFollowMember;

    @Autowired
    private FeedsService feedsService;

    @Override
    public ResponseEntity<Object> getMemberFeed(FindFeedsListObj findFeedsListObj) {
        this.setPage(findFeedsListObj);
        List<Feeds> finded = this.feedsRepository.findByUIdx(findFeedsListObj);
        int total = this.feedTagsRepository.totalCount();

        int followCnt = this.memberFollowMember.countByOIdx(findFeedsListObj.getUIdx());
        int followerCnt = this.memberFollowMember.countByTIdx(findFeedsListObj.getUIdx());
        Member member = this.memberRepository.findByIdx(findFeedsListObj.getUIdx());
        Files files = this.filesRepository.findByIdx(member.getFileIdx());
        Account account = this.accountRepository.findByUserIdx(member.getIdx());

        return this.makeReturn(finded, total, followCnt, followerCnt, member, files, account, findFeedsListObj);
    }

    @Override
    public ResponseEntity<Object> getMemberFeedTogether(FindFeedsListObj findFeedsListObj) {
        this.setPage(findFeedsListObj);
        List<Feeds> finded = this.feedMemberTagRespository.findByUIdx(findFeedsListObj);
        int total = this.feedTagsRepository.totalCount();
        int followCnt = this.memberFollowMember.countByOIdx(findFeedsListObj.getUIdx());
        int followerCnt = this.memberFollowMember.countByTIdx(findFeedsListObj.getUIdx());
        Member member = this.memberRepository.findByIdx(findFeedsListObj.getUIdx());
        Files files = this.filesRepository.findByIdx(member.getFileIdx());
        Account account = this.accountRepository.findByUserIdx(member.getIdx());

        return this.makeReturn(finded, total, followCnt, followerCnt, member, files, account, findFeedsListObj);
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

    private ResponseEntity<Object> makeReturn(List<Feeds> finded, int total, int followCnt, int followerCnt, Member member, Files files, Account account, FindFeedsListObj feeds) {
        List<ResponseMyFeeds> myFeedsArrayList = new ArrayList<>();
        myFeedsArrayList = this.makeCheckListSize(finded, feeds);
        FinalObjMember finalObj = this.makeResponseDataSet(feeds, myFeedsArrayList, total, followCnt, followerCnt, member, files, account);
        return ResponseEntityUtil.makeResultSuccess(finalObj);
    }

    private void setPage(FindFeedsListObj findFeedsListObj) {
        if(findFeedsListObj.getPage() > 1){
            findFeedsListObj.setOffset((findFeedsListObj.getPage() - 1) * findFeedsListObj.getLimit());
        }else{
            findFeedsListObj.setOffset((findFeedsListObj.getPage() - 1));
        }
    }

    private FinalObjMember makeResponseDataSet(FindFeedsListObj feeds, List<ResponseMyFeeds> myFeedsArrayList, int total, int followCnt, int followerCnt, Member member, Files files, Account account) {
        FinalObjMember finalObj = new FinalObjMember();
        finalObj.setLimit(feeds.getLimit());
        if(myFeedsArrayList.size() > 0){
            finalObj.setPage(feeds.getPage() + 1);
        }else{
            finalObj.setPage(feeds.getPage());
        }
        finalObj.setFollowCount(followCnt);
        finalObj.setFollowerCount(followerCnt);
        finalObj.setMember(member);
        finalObj.setFiles(files);
        finalObj.setAccount(account);
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

    private void saveAlarm(Alarm alarm){
        this.alarmRepository.save(alarm);
    }
}
