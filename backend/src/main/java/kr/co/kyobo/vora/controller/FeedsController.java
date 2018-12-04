package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.api.FeedWriteObj;
import kr.co.kyobo.vora.model.api.RequestComment;
import kr.co.kyobo.vora.model.database.*;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import kr.co.kyobo.vora.service.feeds.FeedsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class FeedsController {
    @Autowired
    private FeedsService feedsService;

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FEEDS_WRITE, consumes = {"multipart/form-data"})
    public ResponseEntity<Object> postWrite(@ModelAttribute Feeds feeds,
                                            @RequestParam(value = "locationIdx", required = false) Integer locationIdx,
                                            @RequestParam(value = "address", required = false) String address,
                                            @RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "feedIdx", required = false) Integer feedIdx,
                                            @RequestParam(value = "description", required = false) String description,
                                            @RequestParam(value = "link", required = false) String link,
                                            @RequestParam(value = "mapx", required = false) String mapx,
                                            @RequestParam(value = "mapy", required = false) String mapy,
                                            @RequestParam(value = "roadAddress", required = false) String roadAddress,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "actionTagsBookIdx", required = false) List<Integer> actionTagsBookIdx,
                                            @RequestParam(value = "actionTagsMovieIdx", required = false) List<Integer> actionTagsMovieIdx,
                                            @RequestParam(value = "actionTagsCultureIdx", required = false) List<Integer> actionTagsCultureIdx,
                                            @RequestParam(value = "bookId", required = false) List<String> bookId,
                                            @RequestParam(value = "actor", required = false) List<String> actor,
                                            @RequestParam(value = "director", required = false) List<String> director,
                                            @RequestParam(value = "mImage", required = false) List<String> mImage,
                                            @RequestParam(value = "mLink", required = false) List<String> mLink,
                                            @RequestParam(value = "pubDate", required = false) List<String> pubDate,
                                            @RequestParam(value = "mSubtitle", required = false) List<String> mSubtitle,
                                            @RequestParam(value = "mTitle", required = false) List<String> mTitle,
                                            @RequestParam(value = "userRating", required = false) List<String> userRating,
                                            @RequestParam(value = "cateName1", required = false) List<String> cateName1,
                                            @RequestParam(value = "cImage", required = false) List<String> cImage,
                                            @RequestParam(value = "cLink", required = false) List<String> cLink,
                                            @RequestParam(value = "cLocation", required = false) List<String> cLocation,
                                            @RequestParam(value = "startDate", required = false) List<String> startDate,
                                            @RequestParam(value = "endDate", required = false) List<String> endDate,
                                            @RequestParam(value = "cTitle", required = false) List<String> cTitle,
                                            @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles,
                                            @RequestParam(value = "tag", required = false) List<String> tag,
                                            @RequestParam(value = "tIdx", required = false) List<Integer> tIdx,
                                            HttpServletRequest request
    ) throws IOException {

        FeedLocations feedLocations = new FeedLocations();
        if (locationIdx != null)
            feedLocations.setIdx(locationIdx);
        feedLocations.setAddress(address);
        feedLocations.setCategory(category);
        feedLocations.setDescription(description);
        feedLocations.setLink(link);
        feedLocations.setMapx(mapx);
        feedLocations.setMapy(mapy);
        feedLocations.setRoadAddress(roadAddress);
        feedLocations.setTitle(title);

        FeedWriteObj savefeedWriteObj = this.makeSaveFeedDataSetters(feeds, feedLocations, bookId, actor, director, mImage, mLink, pubDate, mSubtitle, mTitle, userRating, cateName1, cImage, cLink, cLocation, startDate, endDate, cTitle, multipartFiles, tag, tIdx, locationIdx, actionTagsBookIdx, actionTagsMovieIdx, actionTagsCultureIdx);

        return this.feedsService.writeFeeds(savefeedWriteObj, request);
    }

    private FeedWriteObj makeSaveFeedDataSetters(Feeds feeds, FeedLocations feedLocations, List<String> bookId, List<String> actor, List<String> director, List<String> mImage, List<String> mLink, List<String> pubDate, List<String> mSubtitle, List<String> mTitle, List<String> userRating, List<String> cateName1, List<String> cImage, List<String> cLink, List<String> cLocation, List<String> startDate, List<String> endDate, List<String> cTitle, List<MultipartFile> multipartFiles, List<String> tag, List<Integer> tIdx, Integer locationIdx, List<Integer> actionTagsBookIdx, List<Integer> actionTagsMovieIdx, List<Integer> actionTagsCultureIdx) {
        FeedWriteObj feedWriteObj = new FeedWriteObj();

        BeanUtils.copyProperties(feeds, feedWriteObj);
        if (feedLocations != null) {
            if (feedLocations.getAddress() != null) {
                if (locationIdx == 0) {
                    feedWriteObj.setFeedLocations(feedLocations);
                }
            }
        }

        List<ActionTagsBook> saveBooks = new ArrayList<>();
        int cnt = 0;
        if (bookId != null) {
            for (String bookIds : bookId) {
                if (actionTagsBookIdx.get(cnt) == 0) {
                    ActionTagsBook actionTagsBook = new ActionTagsBook();
                    actionTagsBook.setBookId(bookIds);
                    saveBooks.add(actionTagsBook);
                    cnt++;
                }
            }
            cnt = 0;
        }
        feedWriteObj.setSaveBooks(saveBooks);


        List<ActionTagsMovie> saveMovies = new ArrayList<>();
        if (actor != null) {
            for (int i = 0; i < actor.size(); i++) {
                if (actionTagsMovieIdx.get(i) == 0) {
                    ActionTagsMovie actionTagsMovie = new ActionTagsMovie();
                    actionTagsMovie.setActor(actor.get(i));
                    actionTagsMovie.setDirector(director.get(i));
                    actionTagsMovie.setMImage(mImage.get(i));
                    actionTagsMovie.setMLink(mLink.get(i));
                    actionTagsMovie.setMSubtitle(mSubtitle.get(i));
                    actionTagsMovie.setMTitle(mTitle.get(i));
                    actionTagsMovie.setPubDate(pubDate.get(i));
                    actionTagsMovie.setUserRating(userRating.get(i));
                    saveMovies.add(actionTagsMovie);
                }
            }
        }
        feedWriteObj.setSaveMovies(saveMovies);

        List<ActionTagsCultureExhibition> saveCulure = new ArrayList<>();
        if (cateName1 != null) {
            for (int i = 0; i < cateName1.size(); i++) {
                if (actionTagsCultureIdx.get(i) == 0) {
                    ActionTagsCultureExhibition actionTagsCultureExhibition = new ActionTagsCultureExhibition();
                    actionTagsCultureExhibition.setCateName1(cateName1.get(i));
                    actionTagsCultureExhibition.setCImage(cImage.get(i));
                    actionTagsCultureExhibition.setCLink(cLink.get(i));
                    actionTagsCultureExhibition.setCLocation(cLocation.get(i));
                    actionTagsCultureExhibition.setCTitle(cTitle.get(i));
                    actionTagsCultureExhibition.setStartDate(startDate.get(i));
                    actionTagsCultureExhibition.setEndDate(endDate.get(i));
                    saveCulure.add(actionTagsCultureExhibition);
                }
            }
        }
        feedWriteObj.setSaveCultureAndExhibition(saveCulure);
        feedWriteObj.setMultipartFiles(multipartFiles);

        List<Tags> tagsList = new ArrayList<>();
        if (tag != null) {
            for (String t : tag) {
                Tags tags = new Tags();
                tags.setTag(t);
                tagsList.add(tags);
            }
        }
        feedWriteObj.setTags(tagsList);


        List<FeedMemberTag> followers = new ArrayList<>();
        if (tIdx != null) {
            for (Integer idxs : tIdx) {
                FeedMemberTag feedMemberTag = new FeedMemberTag();
                feedMemberTag.setUIdx(idxs);
                followers.add(feedMemberTag);
            }
        }
        feedWriteObj.setFollowers(followers);
        return feedWriteObj;
    }


    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FEED_VIEW)
    public ResponseEntity<Object> getDetailFeed(@RequestBody Feeds feeds) {
        return this.feedsService.findFeedDetailByIdx(feeds, true);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FEED_UPDATE_DATA)
    public ResponseEntity<Object> getSelectedFeed(@RequestBody Feeds feeds) {
        return this.feedsService.findFeedDetailByIdx(feeds, false);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FIND_COMMENT_BY_LIKE)
    public ResponseEntity<Object> getFeedCommentByLike(@RequestBody FindFeedsListObj feeds) {
        return this.feedsService.findCommentByLikeCount(feeds);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FIND_COMMENT_BY_NEWS)
    public ResponseEntity<Object> getFeedCommentByNew(@RequestBody FindFeedsListObj feeds) {
        return this.feedsService.findCommentByCommentIdx(feeds);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.ADD_FEED_COMMENT)
    public ResponseEntity<Object> addFeedCommentParent(@RequestBody RequestComment requestComment) {
        return this.feedsService.addFeedCommentParent(requestComment);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.SET_COMMENT_LIKE_TOGGLE)
    public ResponseEntity<Object> commentLikeToggle(@RequestBody CommentLiker commentLiker) {
        return this.feedsService.commentLikeToggle(commentLiker);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.REMOVE_COMMENT)
    public ResponseEntity<Object> removeComment(@RequestBody FeedComment feedComment) {
        return this.feedsService.deleteFeedComment(feedComment);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.UPDATE_COMMENT)
    public ResponseEntity<Object> updateComment(@RequestBody RequestComment requestComment) {
        return this.feedsService.updateFeedComment(requestComment);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.DELETE_FEED)
    public ResponseEntity<Object> deleteFeed(@RequestBody Feeds feeds) {
        return this.feedsService.deleteFeeds(feeds);
    }

    @PostMapping(path = UriVersion.API_VERSION_PRIVATE + UriEntity.FEEDS + UriMethod.FEEDS_UPDATE, consumes = {"multipart/form-data"})
    public ResponseEntity<Object> postUpdate(@ModelAttribute Feeds feeds,
                                             @RequestParam(value = "locationIdx", required = false) Integer locationIdx,
                                             @RequestParam(value = "address", required = false) String address,
                                             @RequestParam(value = "category", required = false) String category,
                                             @RequestParam(value = "feedIdx", required = false) Integer feedIdx,
                                             @RequestParam(value = "description", required = false) String description,
                                             @RequestParam(value = "link", required = false) String link,
                                             @RequestParam(value = "mapx", required = false) String mapx,
                                             @RequestParam(value = "mapy", required = false) String mapy,
                                             @RequestParam(value = "roadAddress", required = false) String roadAddress,
                                             @RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "actionTagsBookIdx", required = false) List<Integer> actionTagsBookIdx,
                                             @RequestParam(value = "actionTagsMovieIdx", required = false) List<Integer> actionTagsMovieIdx,
                                             @RequestParam(value = "actionTagsCultureIdx", required = false) List<Integer> actionTagsCultureIdx,
                                             @RequestParam(value = "bookId", required = false) List<String> bookId,
                                             @RequestParam(value = "actor", required = false) List<String> actor,
                                             @RequestParam(value = "director", required = false) List<String> director,
                                             @RequestParam(value = "mImage", required = false) List<String> mImage,
                                             @RequestParam(value = "movieIdx", required = false) List<Integer> movieIdx,
                                             @RequestParam(value = "mLink", required = false) List<String> mLink,
                                             @RequestParam(value = "pubDate", required = false) List<String> pubDate,
                                             @RequestParam(value = "mSubtitle", required = false) List<String> mSubtitle,
                                             @RequestParam(value = "mTitle", required = false) List<String> mTitle,
                                             @RequestParam(value = "userRating", required = false) List<String> userRating,
                                             @RequestParam(value = "cultureIdx", required = false) List<Integer> cultureIdx,
                                             @RequestParam(value = "cateName1", required = false) List<String> cateName1,
                                             @RequestParam(value = "cImage", required = false) List<String> cImage,
                                             @RequestParam(value = "cLink", required = false) List<String> cLink,
                                             @RequestParam(value = "cLocation", required = false) List<String> cLocation,
                                             @RequestParam(value = "startDate", required = false) List<String> startDate,
                                             @RequestParam(value = "endDate", required = false) List<String> endDate,
                                             @RequestParam(value = "cTitle", required = false) List<String> cTitle,
                                             @RequestParam(value = "tag", required = false) List<String> tag,
                                             @RequestParam(value = "tIdx", required = false) List<Integer> tIdx,
                                             @RequestParam(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles,
                                             @RequestParam(value = "deleteBookIdx", required = false) List<String> deleteBookIdx,
                                             @RequestParam(value = "deleteFileIdx", required = false) List<Integer> deleteFileIdx,
                                             @RequestParam(value = "deleteMovieIdx", required = false) List<Integer> deleteMovieIdx,
                                             @RequestParam(value = "deleteCultureIdx", required = false) List<Integer> deleteCultureIdx,
                                             @RequestParam(value = "deleteTagIdx", required = false) List<Integer> deleteTagIdx,
                                             @RequestParam(value = "deleteFollowerIdx", required = false) List<Integer> deleteFollowerIdx,
                                             @RequestParam(value = "deleteLocationIdx", required = false) Integer deleteLocationIdx
    ) {

        FeedLocations feedLocations = new FeedLocations();

        feedLocations.setIdx(locationIdx);
        feedLocations.setAddress(address);
        feedLocations.setCategory(category);
        feedLocations.setDescription(description);
        feedLocations.setLink(link);
        feedLocations.setMapx(mapx);
        feedLocations.setMapy(mapy);
        feedLocations.setRoadAddress(roadAddress);
        feedLocations.setTitle(title);

        this.feedsService.feedContentRemover(feeds, deleteBookIdx, deleteFileIdx, deleteMovieIdx, deleteCultureIdx, deleteTagIdx, deleteFollowerIdx, deleteLocationIdx);

        FeedWriteObj savefeedWriteObj = this.makeSaveFeedDataSetters(feeds, feedLocations, bookId, actor, director, mImage, mLink, pubDate, mSubtitle, mTitle, userRating, cateName1, cImage, cLink, cLocation, startDate, endDate, cTitle, multipartFiles, tag, tIdx, locationIdx, actionTagsBookIdx, actionTagsMovieIdx, actionTagsCultureIdx);
        return this.feedsService.updateFeeds(savefeedWriteObj);
    }


}
