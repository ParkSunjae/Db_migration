package kr.co.kyobo.vora.model.api;

import kr.co.kyobo.vora.model.database.CommentMember;
import kr.co.kyobo.vora.model.database.CommentTags;
import kr.co.kyobo.vora.model.database.FeedComment;
import kr.co.kyobo.vora.model.database.Tags;
import lombok.Data;

import java.util.List;

@Data
public class RequestComment {
    private FeedComment feedComment;
    private List<CommentMember> follwers;
    private List<Tags> tags;
}
