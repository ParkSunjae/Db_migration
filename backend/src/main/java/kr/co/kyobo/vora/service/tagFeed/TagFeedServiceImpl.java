package kr.co.kyobo.vora.service.tagFeed;

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
public class TagFeedServiceImpl implements TagFeedService {

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
    private FeedsService feedsService;
    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public ResponseEntity<Object> getTagFeed(TagRelationFeedRequest tagRelationFeedRequest) {
        this.setPage(tagRelationFeedRequest);
        List<Feeds> finded = this.feedTagsRepository.selectFeedsByTagidx(tagRelationFeedRequest);
        int total = this.feedTagsRepository.totalCount();
        int tagFollowCnt = tagFollowRepository.countByTagIdx(tagRelationFeedRequest.getIdx());

        Tags tags = this.tagsRepository.findByIdx(tagRelationFeedRequest.getIdx());

        return this.makeReturn(finded, total, tagRelationFeedRequest,tagFollowCnt,tags);
    }

    @Override
    public ResponseEntity<Object> getTagFeedPopular(TagRelationFeedRequest tagRelationFeedRequest) {
        this.setPage(tagRelationFeedRequest);
        List<Feeds> finded = this.feedTagsRepository.selectFeedsOnPopularByTagidx(tagRelationFeedRequest);
        int total = this.feedTagsRepository.totalCount();
        int tagFollowCnt = tagFollowRepository.countByTagIdx(tagRelationFeedRequest.getIdx());

        Tags tags = this.tagsRepository.findByIdx(tagRelationFeedRequest.getIdx());

        return this.makeReturn(finded, total, tagRelationFeedRequest,tagFollowCnt,tags);
    }

    private List<ResponseMyFeeds> makeCheckListSize(List<Feeds> finded, TagRelationFeedRequest feeds) {
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

    private ResponseEntity<Object> makeReturn(List<Feeds> finded, int total, TagRelationFeedRequest feeds, int tagFollowCount, Tags tags) {
        List<ResponseMyFeeds> myFeedsArrayList = this.makeCheckListSize(finded, feeds);
        FinalObjTags finalObj = this.makeResponseDataSet(feeds, myFeedsArrayList, total, tagFollowCount,tags);
        return ResponseEntityUtil.makeResultSuccess(finalObj);
    }

    private void setPage(TagRelationFeedRequest tagRelationFeedRequest) {
        if(tagRelationFeedRequest.getPage() > 1){
            tagRelationFeedRequest.setOffset((tagRelationFeedRequest.getPage() - 1) * tagRelationFeedRequest.getLimit());
        }else{
            tagRelationFeedRequest.setOffset((tagRelationFeedRequest.getPage() - 1));
        }
    }

    private FinalObjTags makeResponseDataSet(TagRelationFeedRequest feeds, List<ResponseMyFeeds> myFeedsArrayList, int total, int tagFollowCount, Tags tags) {
        FinalObjTags finalObj = new FinalObjTags();
        finalObj.setLimit(feeds.getLimit());
        if(myFeedsArrayList.size() > 0){
            finalObj.setPage(feeds.getPage() + 1);
        }else{
            finalObj.setPage(feeds.getPage());
        }
        finalObj.setFollowCount(tagFollowCount);
        finalObj.setTotalCount(total);
        finalObj.setRtns(myFeedsArrayList);
        finalObj.setTagsInfo(tags);
        return finalObj;
    }

    private List<ResponseMyFeeds> feedInfofind(TagRelationFeedRequest feeds, List<Feeds> finded){
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
