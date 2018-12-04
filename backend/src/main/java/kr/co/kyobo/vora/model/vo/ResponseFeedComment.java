package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.FeedComment;
import kr.co.kyobo.vora.model.database.Files;
import kr.co.kyobo.vora.model.database.Member;
import lombok.Data;

@Data
public class ResponseFeedComment extends FeedComment {
    Member member;
    Files files;
    boolean feedCommentMenu = false;
}
