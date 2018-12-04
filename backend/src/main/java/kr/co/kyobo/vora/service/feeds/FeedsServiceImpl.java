package kr.co.kyobo.vora.service.feeds;

import kr.co.kyobo.vora.messages.ErrorMessages;
import kr.co.kyobo.vora.model.api.FeedWriteObj;
import kr.co.kyobo.vora.model.api.RequestComment;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.*;
import kr.co.kyobo.vora.repository.*;
import kr.co.kyobo.vora.service.file.AmazonClient;
import kr.co.kyobo.vora.service.getIp.IpAddressToCityName;
import kr.co.kyobo.vora.util.TagsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@Transactional
public class FeedsServiceImpl implements FeedsService {

    @Value("${postImage.path}")
    private String feedPath;

    @Value("${postImageThm.path}")
    private String feedThumbnailPath;

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
    private AccountRepository accountRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FeedCommentRepository feedCommentRepository;
    @Autowired
    private FeedLikerRepository feedLikerRepository;
    @Autowired
    private MemberScrapFeedsRepository memberScrapFeedsRepository;
    @Autowired
    private CommentLikerRepository commentLikerRepository;
    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private CommentMemberRepository commentMemberRepository;
    @Autowired
    private CommentTagRepository commentTagRepository;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private IpAddressToCityName ipAddressToCityName;

    @Override
    public ResponseEntity<Object> postFeeds(Feeds feeds, HttpServletResponse response) {

        int result = feedsRepository.save(feeds);

        List<Tags> tags = TagsUtil.parseTags(feeds.getContents());
        if (tags.size() > 0) {
            List<Tags> savedTags = tagsRepository.findByTagMulti(tags);
            List<Tags> newTags = new ArrayList<>();
            boolean itsNew;
            for (Tags tag : tags) {
                itsNew = true;
                for (Tags tag_buff : savedTags) {
                    if (tag.getTag().equals(tag_buff.getTag())) itsNew = false;
                }
                if (itsNew)
                    newTags.add(tag);
            }
            if (newTags.size() > 0)
                tagsRepository.saveMulti(newTags);

            tags = savedTags;
            tags.addAll(newTags);
        }

        List<FeedTags> feedTagsList = new ArrayList<>();
        for (Tags tag : tags) {
            FeedTags ft = new FeedTags();
            ft.setTagsIdx(tag.getIdx());
            ft.setFeedIdx(feeds.getIdx());
            feedTagsList.add(ft);
        }
        feedTagsRepository.saveMulti(feedTagsList);

        HashMap<String, Object> resultMap = new HashMap<>();
        if (result != 1) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_INSERT_FAIL.getCode());
        } else {
            resultMap.put("SaveSuccess", true);
            resultMap.put("FeedIndex", feeds.getIdx());
        }
        return ResponseEntityUtil.makeResultSuccess(resultMap);
    }

    @Override
    public ResponseEntity<Object> modifyFeeds(Feeds feeds, HttpServletResponse response) {
        int result = feedsRepository.updateFeed(feeds);
        HashMap<String, Object> resultMap = new HashMap<>();
        if (result != 1) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_UPDATE_FAIL.getCode());
        } else {
            resultMap.put("UpdateSuccess", true);
            resultMap.put("FeedIndex", feeds.getIdx());
        }
        return ResponseEntityUtil.makeResultSuccess(resultMap);
    }

    @Override
    public ResponseEntity<Object> deleteFeeds(Feeds feeds) {

        // 파일 삭제
        List<Files> feedFiles = this.feedFilesRepository.findByFeedIdx(feeds);

        for (Files files : feedFiles) {
            log.info(files.toString());
            amazonClient.deleteFileFromS3Bucket(files.getFileName(), feedPath);
            amazonClient.deleteFileFromS3Bucket(files.getFileThumbnail(), feedThumbnailPath);
        }

        int result = feedsRepository.delete(feeds);
        HashMap<String, Object> resultMap = new HashMap<>();
        if (result != 1) {
            return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_DB_DELETE_FAIL.getCode());
        } else {
            resultMap.put("DeleteSuccess", true);
        }
        return ResponseEntityUtil.makeResultSuccess(resultMap);
    }

    @Override
    public ResponseEntity<Object> writeFeeds(FeedWriteObj feedWriteObj, HttpServletRequest request) throws IOException {

        feedWriteObj.setLocationText(this.ipAddressToCityName.callCityName(request));

        // 피드저장
        int rtn = this.feedsRepository.save(feedWriteObj);

        this.saveFeedContent(feedWriteObj);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    private void saveFeedContent(FeedWriteObj feedWriteObj) {
        // 위치저장
        if (feedWriteObj.getFeedLocations() != null) {
            if (!(feedWriteObj.getFeedLocations().getTitle() == null || feedWriteObj.getFeedLocations().getTitle().equals(""))) {
                feedWriteObj.getFeedLocations().setFeedIdx(feedWriteObj.getIdx());
                this.feedLocationRepository.saveLocation(feedWriteObj.getFeedLocations());
            }
        }

        //액션 태그 생성 Book
        if (feedWriteObj.getSaveBooks().size() > 0) {
            ActionTags actionTagsAndBook = new ActionTags();
            actionTagsAndBook.setFeedIdx(feedWriteObj.getIdx());
            actionTagsAndBook.setActionTagType("b");

            this.actionTagRepository.saveActionTag(actionTagsAndBook);


            //책저장
            List<ActionTagsBook> saveBooks = new ArrayList<>();
            for (ActionTagsBook actionTagsBook : feedWriteObj.getSaveBooks()) {
                actionTagsBook.setActionTagsIdx(actionTagsAndBook.getIdx());
                saveBooks.add(actionTagsBook);
            }

            this.actionTagBookRepository.saveMulti(saveBooks);
        }

        if (feedWriteObj.getSaveMovies().size() > 0) {
            //액션 태그 생성 Movie
            ActionTags actionTagsAndMovie = new ActionTags();
            actionTagsAndMovie.setFeedIdx(feedWriteObj.getIdx());
            actionTagsAndMovie.setActionTagType("m");

            this.actionTagRepository.saveActionTag(actionTagsAndMovie);

            //영화저장
            List<ActionTagsMovie> saveMovies = new ArrayList<>();
            for (ActionTagsMovie actionTagsMovie : feedWriteObj.getSaveMovies()) {
                actionTagsMovie.setActionTagsIdx(actionTagsAndMovie.getIdx());
                saveMovies.add(actionTagsMovie);
            }

            this.actionTagMovieRepository.saveMulti(saveMovies);
        }

        if (feedWriteObj.getSaveCultureAndExhibition().size() > 0) {
            //액션 태그 생성 Movie
            ActionTags actionTagsAndCulture = new ActionTags();
            actionTagsAndCulture.setFeedIdx(feedWriteObj.getIdx());
            actionTagsAndCulture.setActionTagType("c");

            this.actionTagRepository.saveActionTag(actionTagsAndCulture);

            //공연 & 전시저장
            List<ActionTagsCultureExhibition> saveCulture = new ArrayList<>();
            for (ActionTagsCultureExhibition actionTagsCultureExhibition : feedWriteObj.getSaveCultureAndExhibition()) {
                actionTagsCultureExhibition.setActionTagsIdx(actionTagsAndCulture.getIdx());
                saveCulture.add(actionTagsCultureExhibition);
            }

            this.actionTagCultureExhibitionRepository.saveMulti(saveCulture);
        }

        //태그저장
        if (feedWriteObj.getTags().size() > 0) {
            //태그 중복 제거
            List<Tags> noDuplList = feedWriteObj.getTags();
            HashSet<Tags> listSet = new HashSet<>(noDuplList);
            List<Tags> processedList = new ArrayList<>(listSet);

            //tag count 처리
            for (Tags nodup : processedList) {
                for (Tags tags : feedWriteObj.getTags()) {
                    if (nodup.getTag().equals(tags.getTag())) {
                        log.info("hitcount === " + nodup.getHits());
                        nodup.setHits(nodup.getHits() + 1);
                    }
                }

                // 태그 저장
                int tagIdx = 0;
                Tags finded = this.tagsRepository.findByTag(nodup);
                if (finded != null) {
                    //tag update
                    finded.setHits(nodup.getHits());
                    this.tagsRepository.updateHitsCountTags(finded);
                    tagIdx = finded.getIdx();
                } else {
                    //insert tag
                    this.tagsRepository.save(nodup);
                    tagIdx = nodup.getIdx();
                }
                //태그 & 피드 연결 저장
                FeedTags feedTags = new FeedTags();
                feedTags.setFeedIdx(feedWriteObj.getIdx());
                feedTags.setTagsIdx(tagIdx);
                this.feedTagsRepository.save(feedTags);
            }
        }

        // 팔로워 저장
        if (feedWriteObj.getFollowers().size() > 0) {
            for (FeedMemberTag feedMemberTag : feedWriteObj.getFollowers()) {
                feedMemberTag.setFeedIdx(feedWriteObj.getIdx());

                int check = this.feedMemberTagRespository.findFeedIdxAndUidx(feedMemberTag);
                if (check == 0) {
                    this.feedMemberTagRespository.save(feedMemberTag);
                }
                if (feedWriteObj.getUIdx() != feedMemberTag.getUIdx()) {


                    Member findMember = this.memberRepository.findByIdx(feedWriteObj.getUIdx());

                    //TODO push 발송
                    // alarm 등록
                    Alarm alarm = new Alarm();
                    alarm.setFeedIdx(feedWriteObj.getIdx());
                    alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 피드에서 회원님을 언급 하였습니다.");
                    alarm.setUIdx(feedWriteObj.getUIdx());
                    alarm.setShowYn("N");
                    alarm.setTargetIdx(feedMemberTag.getUIdx());
                    alarm.setType("f");
                    this.saveAlarm(alarm);
                }
            }
        }

        List<Files> fileNames = new ArrayList<>();
        if (feedWriteObj.getMultipartFiles().size() > 0) {
            // 파일저장
            for (MultipartFile multipartFile : feedWriteObj.getMultipartFiles()) {
                Files files = new Files();
                String fileName = this.amazonClient.uploadFile(multipartFile, this.feedPath);
                String thumb = this.amazonClient.uploadFileThumbnail(multipartFile, this.feedThumbnailPath);
                String[] splits = multipartFile.getOriginalFilename().split("\\.");


                files.setFileName(fileName);
                files.setFileThumbnail(thumb);
                files.setFileType(splits[1]);

                this.filesRepository.saveFile(files);
                fileNames.add(files);
            }
        }


        if (fileNames.size() > 0) {
            //파일 - 피드 연관관계 설정
            List<FeedFiles> saveFeedFiles = new ArrayList<>();
            for (Files files : fileNames) {
                FeedFiles feedFiles = new FeedFiles();

                feedFiles.setFeedIdx(feedWriteObj.getIdx());
                feedFiles.setFileIdx(files.getIdx());
                saveFeedFiles.add(feedFiles);
            }
            this.feedFilesRepository.saveMulti(saveFeedFiles);
        }
    }

    private void saveAlarm(Alarm alarm) {
        this.alarmRepository.save(alarm);
    }

    @Override
    public ResponseEntity<Object> findFeedDetailByIdx(Feeds feeds, Boolean viewChcker) {
        return ResponseEntityUtil.makeResultSuccess(this.findByFeedIdx(feeds, viewChcker));
    }

    @Override
    public ResponseEntity<Object> findCommentByLikeCount(FindFeedsListObj feeds) {
        this.setPage(feeds);
        // 피드의 부모 코멘트 목록 찾는다.
        List<FeedComment> parents = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByLikeCount(feeds);
        int totalCount = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByLikeCountsTotalCount();
        FinalCommentObj finalCommentObj = new FinalCommentObj();

        finalCommentObj.setPage(feeds.getPage());
        finalCommentObj.setTotalCount(totalCount);
        finalCommentObj.setLimit(feeds.getLimit());
        finalCommentObj.setRtns(this.makeCommentList(feeds, parents, true));

        return ResponseEntityUtil.makeResultSuccess(finalCommentObj);
    }


    @Override
    public ResponseEntity<Object> findCommentByCommentIdx(FindFeedsListObj feeds) {
        this.setPage(feeds);
        // 피드의 부모 코멘트 목록 찾는다.
        List<FeedComment> parents = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByIdxAsc(feeds);
        int totalCount = this.feedCommentRepository.findParentCommnetByFeedsIdxOrderByIdxAscTotalCount();
        FinalCommentObj finalCommentObj = new FinalCommentObj();

        finalCommentObj.setPage(feeds.getPage());
        finalCommentObj.setTotalCount(totalCount);
        finalCommentObj.setLimit(feeds.getLimit());
        finalCommentObj.setRtns(this.makeCommentList(feeds, parents, false));

        return ResponseEntityUtil.makeResultSuccess(finalCommentObj);
    }

    @Override
    public ResponseEntity<Object> addFeedCommentParent(RequestComment requestComment) {
        int rtn = this.feedCommentRepository.saveParent(requestComment.getFeedComment());

        this.makeAlarmAndRelationComment(requestComment);

        return ResponseEntityUtil.makeResultSuccess(requestComment);
    }

    private void makeAlarmAndRelationComment(RequestComment requestComment) {

        //requestComment 의 uidx 코멘트 작성자
        //feeds의 uidx는 피드 작성자
        //requestComment 의 parentIdx는 부모 코멘트 작성자
        //requestComment.getFollwers() 언급자


        //TODO push
        Alarm alarm = new Alarm();
        Feeds setFeed = new Feeds();
        setFeed.setIdx(requestComment.getFeedComment().getFeedIdx());

        Feeds feeds = this.feedsRepository.findFeedsByIdx(setFeed);
        Member findMember = this.memberRepository.findByIdx(requestComment.getFeedComment().getUIdx());

        Boolean feedCheck = true;
        Boolean commentCheck = true;

        // 언급자 처리
        if (requestComment.getFollwers().size() > 0) {
            for (CommentMember commentMember : requestComment.getFollwers()) {
                if(feeds.getUIdx() == commentMember.getUIdx()){
                    feedCheck = false;
                    break;
                }
            }
            for (CommentMember commentMember : requestComment.getFollwers()) {
                commentMember.setCommentIdx(requestComment.getFeedComment().getIdx());
                //코멘트 작성자와 언급자가 같지 않을 때만 알림을 보낸다.
                if (requestComment.getFeedComment().getUIdx() != commentMember.getUIdx()) {
                    //TODO push
                    alarm = new Alarm();
                    alarm.setFeedIdx(requestComment.getFeedComment().getFeedIdx());
                    alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님을 댓글에서 언급 하였습니다.");
                    alarm.setUIdx(requestComment.getFeedComment().getUIdx());
                    alarm.setShowYn("N");
                    alarm.setTargetIdx(commentMember.getUIdx());
                    alarm.setType("c");
                    this.saveAlarm(alarm);
                }
            }
            this.commentMemberRepository.saveMulti(requestComment.getFollwers());
        }

        //코멘트 작성자와 피드 작성자가 같지 않으면 알림을 보낸다
        if (feedCheck && feeds.getUIdx() != requestComment.getFeedComment().getUIdx()) {
            //TODO push
            // alarm 등록
            alarm = new Alarm();
            alarm.setFeedIdx(requestComment.getFeedComment().getFeedIdx());
            alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님 피드에 댓글을 작성 하였습니다.");
            alarm.setUIdx(requestComment.getFeedComment().getUIdx());
            alarm.setShowYn("N");
            alarm.setTargetIdx(feeds.getUIdx());
            alarm.setType("p");
            this.saveAlarm(alarm);

        }
        if (requestComment.getFeedComment().getParentIdx() != 0) {
            //TODO push
            FeedComment parent2 = this.feedCommentRepository.findByCommentIdx(requestComment.getFeedComment().getParentIdx());
            //부모댓글 작성자와 자식 댓글 작성자가 같지 않으면 알림을 보낸다.
            if (feedCheck && requestComment.getFeedComment().getUIdx() != parent2.getUIdx()) {
                alarm = new Alarm();
                alarm.setFeedIdx(requestComment.getFeedComment().getFeedIdx());
                alarm.setMessage("<span>" + findMember.getNickName() + "</span> 님이 회원님 댓글에 댓글을 작성 하였습니다.");
                alarm.setUIdx(requestComment.getFeedComment().getUIdx());
                alarm.setShowYn("N");
                alarm.setTargetIdx(parent2.getUIdx());
                alarm.setType("c");
                this.saveAlarm(alarm);
            }
        }


        if (requestComment.getTags().size() > 0) {
            List<Tags> noDuplList = requestComment.getTags();
            HashSet<Tags> listSet = new HashSet<>(noDuplList);
            List<Tags> processedList = new ArrayList<>(listSet);

            //tag count 처리
            for (Tags nodup : processedList) {
                for (Tags tags : requestComment.getTags()) {
                    if (nodup.getTag().equals(tags.getTag())) {
                        log.info("hitcount === " + nodup.getHits());
                        nodup.setHits(nodup.getHits() + 1);
                    }
                }

                // 태그 저장
                int tagIdx = 0;
                Tags finded = this.tagsRepository.findByTag(nodup);
                if (finded != null) {
                    finded.setHits(nodup.getHits());
                    this.tagsRepository.updateHitsCountTags(finded);
                    tagIdx = finded.getIdx();
                } else {
                    //insert tag
                    this.tagsRepository.save(nodup);
                    tagIdx = nodup.getIdx();
                }
                //태그 & 코멘트 연결 저장
                CommentTags commentTags = new CommentTags();
                commentTags.setCommentIdx(requestComment.getFeedComment().getIdx());
                commentTags.setTagIdx(tagIdx);
                this.commentTagRepository.save(commentTags);
            }
        }

    }

    @Override
    public ResponseEntity<Object> commentLikeToggle(CommentLiker commentLiker) {

        CommentLiker finded = this.commentLikerRepository.checkLikeUser(commentLiker);

        if (finded != null) {
            this.commentLikerRepository.changeCommentLikeFalse(commentLiker);
            this.feedCommentRepository.updateLikesFalseByCommentIdx(commentLiker.getCommentIdx());
        } else {
            this.commentLikerRepository.changeCommentLikeTrue(commentLiker);
            this.feedCommentRepository.updateLikesTrueByCommentIdx(commentLiker.getCommentIdx());
        }

        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> deleteFeedComment(FeedComment feedComment) {

        this.commentLikerRepository.deleteByCommentParentIdx(feedComment.getIdx());
        this.feedCommentRepository.deleteByCommentChildIdx(feedComment);
        this.commentLikerRepository.deleteByCommentIdx(feedComment.getIdx());
        this.feedCommentRepository.deleteByCommentIdx(feedComment);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public ResponseEntity<Object> updateFeedComment(RequestComment requestComment) {

        this.feedCommentRepository.updateComment(requestComment.getFeedComment());

        this.commentMemberRepository.deleteByCommentIdx(requestComment.getFeedComment().getIdx());
        this.commentTagRepository.deleteByCommentIdx(requestComment.getFeedComment().getIdx());
        this.makeAlarmAndRelationComment(requestComment);

        return ResponseEntityUtil.makeResultSuccess("");
    }

    @Override
    public void feedContentRemover(Feeds
                                           feeds, List<String> deleteBookIdx, List<Integer> deleteFileIdx, List<Integer> deleteMovieIdx, List<Integer> deleteCultureIdx, List<Integer> deleteTagIdx, List<Integer> deleteFollowerIdx, Integer
                                           deleteLocationIdx) {
        HashMap<String, Object> param = null;
        if (deleteBookIdx.size() > 0) {
            //액션 태그 연결 책 삭제
            param = new HashMap<>();
            param.put("idx", feeds.getIdx());
            param.put("list", deleteBookIdx);
            this.actionTagBookRepository.removeMultiBook(param);
        }
        if (deleteMovieIdx.size() > 0) {
            //액션 태그 연결 영화 삭제
            param = new HashMap<>();
            param.put("idx", feeds.getIdx());
            param.put("list", deleteMovieIdx);
            this.actionTagMovieRepository.removeMulti(param);
        }
        if (deleteCultureIdx.size() > 0) {
            //액션 태그 연결 공연 & 전시 삭제
            param = new HashMap<>();
            param.put("idx", feeds.getIdx());
            param.put("list", deleteCultureIdx);
            this.actionTagCultureExhibitionRepository.removeMulti(param);
        }
        if (deleteFileIdx.size() > 0) {
            //피드 연결 파일 삭제
            param = new HashMap<>();
            param.put("idx", feeds.getIdx());
            param.put("list", deleteFileIdx);
            this.feedFilesRepository.removeMulti(param);
            List<Files> delFiles = this.filesRepository.findMulti(deleteFileIdx);
            //파일 삭제
            for (Files files : delFiles) {
                this.amazonClient.deleteFileFromS3Bucket(files.getFileName(), feedPath);
                this.amazonClient.deleteFileFromS3Bucket(files.getFileThumbnail(), feedThumbnailPath);
                this.filesRepository.deleteByIdx(files.getIdx());
            }
        }

        //피드 위치 정보 삭제
        if (deleteLocationIdx != null && deleteLocationIdx != 0) {
            this.feedLocationRepository.remove(deleteLocationIdx);
        }

        //피드 연결 태그 삭제
        if (deleteTagIdx.size() > 0) {
            for (Integer i : deleteTagIdx) {
                this.feedTagsRepository.removeByFeedIdxAndTagIdx(feeds.getIdx(), i);
            }
        }

        //피드 연결 사용자 삭제
        if (deleteFollowerIdx.size() > 0) {
            for (Integer i : deleteFollowerIdx) {
                this.feedMemberTagRespository.removeByFeedIdxAndmemberIdx(feeds.getIdx(), i);
            }
        }
    }

    @Override
    public ResponseEntity<Object> updateFeeds(FeedWriteObj feedWriteObj) {

        // 피드 수정
        int rtn = this.feedsRepository.updateFeed(feedWriteObj);

        if (rtn == 0) {

        }

        this.saveFeedContent(feedWriteObj);
        return null;
    }

    @Override
    public List<FeedCommentParent> makeCommentList(FindFeedsListObj feeds, List<FeedComment> parents, Boolean
            checker) {
        List<FeedCommentParent> parentList = new ArrayList<>();
        // 포문으로 부모 코멘트의 자식 코멘트 목록을 찾는다.

        for (FeedComment feedComment : parents) {
            FeedCommentParent feedCommentParent = new FeedCommentParent();
            BeanUtils.copyProperties(feedComment, feedCommentParent);

            Member member = this.memberRepository.findByIdx(feedComment.getUIdx());
            feedCommentParent.setCommentUser(member);

            Files memberProfile = new Files();
            memberProfile = this.filesRepository.findByIdx(member.getFileIdx());
            feedCommentParent.setMemberProfile(memberProfile);

            List<CommentLiker> commentLikers = this.commentLikerRepository.findByCommentIdx(feedComment.getIdx());
            feedCommentParent.setCommentLikers(commentLikers);

            feedCommentParent.setMeThisCommentLike(false);
            if (commentLikers.size() > 0) {
                for (CommentLiker commentLiker : commentLikers) {
                    if (feeds.getUIdx() == commentLiker.getUIdx()) {
                        feedCommentParent.setMeThisCommentLike(true);
                    }
                }
            }
            List<Tags> getTagsP = this.commentTagRepository.findTagByCommentIdx(feedComment.getIdx());
            feedCommentParent.setCommentTags(getTagsP);
            List<Member> getMemberP = this.commentMemberRepository.findMemberByCommentIdx(feedComment.getIdx());
            feedCommentParent.setCommentMemberTag(getMemberP);

            feeds.setParentIdx(feedComment.getIdx());
            List<FeedComment> childs = new ArrayList<>();
            if (checker) {
                childs = this.feedCommentRepository.findChildCommnetByFeedsIdxOrderByLikeCount(feeds);
            } else {
                childs = this.feedCommentRepository.findChildCommnetByFeedsIdxOrderByIdxAsc(feeds);
            }
            List<FeedCommentChild> saveObj = new ArrayList<>();
            for (FeedComment childrens : childs) {
                FeedCommentChild feedCommentChild = new FeedCommentChild();
                BeanUtils.copyProperties(childrens, feedCommentChild);

                Member childMember = this.memberRepository.findByIdx(childrens.getUIdx());
                feedCommentChild.setCommentUser(childMember);

                Files childMemberProfile = new Files();
                childMemberProfile = this.filesRepository.findByIdx(childMember.getFileIdx());
                feedCommentChild.setMemberProfile(childMemberProfile);

                List<CommentLiker> childCommentLikers = this.commentLikerRepository.findByCommentIdx(childrens.getIdx());
                feedCommentChild.setCommentLikers(commentLikers);

                feedCommentChild.setMeThisCommentLike(false);
                if (childCommentLikers.size() > 0) {
                    for (CommentLiker commentLiker1 : childCommentLikers) {
                        if (feeds.getUIdx() == commentLiker1.getUIdx()) {
                            feedCommentChild.setMeThisCommentLike(true);
                        }
                    }
                }

                List<Tags> getTagsC = this.commentTagRepository.findTagByCommentIdx(childrens.getIdx());
                feedCommentChild.setCommentTags(getTagsC);
                List<Member> getMemberC = this.commentMemberRepository.findMemberByCommentIdx(childrens.getIdx());
                feedCommentChild.setCommentMemberTag(getMemberC);

                saveObj.add(feedCommentChild);
            }

            feedCommentParent.setChildren(saveObj);
            parentList.add(feedCommentParent);
        }
        return parentList;
    }


    private void setPage(FindFeedsListObj feeds) {
        if (feeds.getPage() > 1) {
            feeds.setOffset((feeds.getPage() - 1) * feeds.getLimit());
        } else {
            feeds.setOffset((feeds.getPage() - 1));
        }
    }


    private ResponseMyFeeds findByFeedIdx(Feeds feeds, Boolean viewChecker) {

        Feeds finded = this.feedsRepository.findFeedsByIdx(feeds);
        if (viewChecker) {
            this.feedsRepository.updateHitsFeed(feeds);
            feeds.setHits(feeds.getHits() + 1);
        }


        ResponseMyFeeds responseMyFeeds = new ResponseMyFeeds();
        BeanUtils.copyProperties(finded, responseMyFeeds);

        // 사용자 정보
        Member member = this.memberRepository.findByIdx(finded.getUIdx());
        responseMyFeeds.setMember(member);
        Files memberProfile = this.filesRepository.findByIdx(member.getFileIdx());
        responseMyFeeds.setMemberProfile(memberProfile);

        // 계정 정보
        Account account = this.accountRepository.findByUserIdx(member.getIdx());
        responseMyFeeds.setAccount(account);

        // 피드의 코멘트 정보
        List<ResponseFeedComment> comments = this.feedCommentRepository.findByFeedsIdxOnResonse(finded.getIdx());
        responseMyFeeds.setComments(comments);
        responseMyFeeds.setCommentCount(comments.size());

        Boolean meLikeCheck = false;
        Boolean meScrapCheck = false;
        Boolean meCommentCheck = false;
        // 피드의 좋아요 정보
        List<FeedLiker> likers = this.feedLikerRepository.findByFeedsIdx(finded.getIdx());
        for (FeedLiker feedLiker : likers) {
            if (feedLiker.getUIdx() == feeds.getUIdx()) {
                meLikeCheck = true;
            }
        }
        responseMyFeeds.setMeLikeCheck(meLikeCheck);
        responseMyFeeds.setLikers(likers);
        responseMyFeeds.setLikeCount(likers.size());

        // 피드의 스크랍 여부 정보
        List<MemberScrapFeeds> scrapFeeds = this.memberScrapFeedsRepository.findByFeedIdx(finded.getIdx());
        for (MemberScrapFeeds memberScrapFeeds : scrapFeeds) {
            if (memberScrapFeeds.getUIdx() == feeds.getUIdx()) {
                meScrapCheck = true;
            }
        }

        Feeds feeds2 = new Feeds();
        feeds2.setIdx(feeds.getIdx());
        feeds2.setUIdx(feeds.getUIdx());


        int checkComment = this.feedCommentRepository.findMyCommentByFeedIdxAndUidx(feeds2);
        if (checkComment > 0) {
            meCommentCheck = true;
        }
        log.info("meCommentCheck==" + meCommentCheck.toString());
        responseMyFeeds.setMeCommentCheck(meCommentCheck);

        responseMyFeeds.setMeScrapCheck(meScrapCheck);
        responseMyFeeds.setScraps(scrapFeeds);
        responseMyFeeds.setScrapCount(scrapFeeds.size());

        // 위치 정보
        FeedLocations feedLocations = this.feedLocationRepository.findByFeedIdx(feeds);
        responseMyFeeds.setFeedLocations(feedLocations);


        //액션 태그 list
        List<ActionTags> actionTags = this.actionTagRepository.findActionTagBookByFeedIdx(feeds);

        for (ActionTags actionTags1 : actionTags) {
            if (actionTags1.getActionTagType().equals("b")) {
                List<RtnVoraBook> books = this.actionTagBookRepository.findByActionTagIdx(feeds.getIdx());
                responseMyFeeds.setBooks(books);
            }
            if (actionTags1.getActionTagType().equals("m")) {
                List<ActionTagsMovie> actionTagsMovies = this.actionTagMovieRepository.findByActionTagIdx(feeds.getIdx());
                responseMyFeeds.setMovies(actionTagsMovies);
            }
            if (actionTags1.getActionTagType().equals("c")) {
                List<ActionTagsCultureExhibition> actionTagsCultureExhibitions = this.actionTagCultureExhibitionRepository.findByActionTagIdx(feeds.getIdx());
                responseMyFeeds.setCulture(actionTagsCultureExhibitions);
            }
        }

        // 파일정보
        List<Files> files = this.filesRepository.findByFeedIdx(finded.getIdx());
        responseMyFeeds.setFiles(files);
        responseMyFeeds.setFileCount(files.size());

        // 피드연관 태그 정보
        List<Tags> findTags = this.tagsRepository.findByFeedIdx(finded.getIdx());
        responseMyFeeds.setFeedTags(findTags);

        // 피드연관 팔로워의 게시물
        List<Member> findFeedMemberTags = this.feedMemberTagRespository.findByFeedIdx(finded.getIdx());
        responseMyFeeds.setFeedMemberTag(findFeedMemberTags);

        return responseMyFeeds;
    }


}
