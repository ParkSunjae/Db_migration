package kr.co.kyobo.vora.model.vo;

import kr.co.kyobo.vora.model.database.*;
import lombok.Data;

import java.util.List;

@Data
public class FeedCommentChild extends FeedComment {
    Member commentUser;
    Files memberProfile;
    Boolean meThisCommentLike;
    List<CommentLiker> commentLikers;
    List<Tags> commentTags;
    List<Member> commentMemberTag;

    Boolean showMenuBool = false;
    Boolean editBool = false;
    Boolean reportBool = false;
    Boolean delete = false;
}
