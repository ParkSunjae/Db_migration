package kr.co.kyobo.vora.service.feeds;

import kr.co.kyobo.vora.model.api.FeedWriteObj;
import kr.co.kyobo.vora.model.api.RequestComment;
import kr.co.kyobo.vora.model.database.CommentLiker;
import kr.co.kyobo.vora.model.database.FeedComment;
import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.vo.FeedCommentParent;
import kr.co.kyobo.vora.model.vo.FindFeedsListObj;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FeedsService {

    ResponseEntity<Object> postFeeds(Feeds feeds, HttpServletResponse response);

    ResponseEntity<Object> modifyFeeds(Feeds feeds, HttpServletResponse response);

    ResponseEntity<Object> deleteFeeds(Feeds feeds);

    ResponseEntity<Object> writeFeeds(FeedWriteObj feedWriteObj, HttpServletRequest request) throws IOException;

    ResponseEntity<Object> findFeedDetailByIdx(Feeds feeds, Boolean viewChcker);

    ResponseEntity<Object> findCommentByLikeCount(FindFeedsListObj feeds);

    ResponseEntity<Object> findCommentByCommentIdx(FindFeedsListObj feeds);

    ResponseEntity<Object> addFeedCommentParent(RequestComment requestComment);

    ResponseEntity<Object> commentLikeToggle(CommentLiker feedComment);

    ResponseEntity<Object> deleteFeedComment(FeedComment feedComment);

    ResponseEntity<Object> updateFeedComment(RequestComment requestComment);

    void feedContentRemover(Feeds feeds, List<String> deleteBookIdx, List<Integer> deleteFileIdx, List<Integer> deleteMovieIdx, List<Integer> deleteCultureIdx, List<Integer> deleteTagIdx, List<Integer> deleteFollowerIdx, Integer deleteLocationIdx);

    ResponseEntity<Object> updateFeeds(FeedWriteObj savefeedWriteObj);

    public List<FeedCommentParent> makeCommentList(FindFeedsListObj feeds, List<FeedComment> parents, Boolean checker);
}
