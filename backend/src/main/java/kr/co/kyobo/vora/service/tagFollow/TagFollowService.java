package kr.co.kyobo.vora.service.tagFollow;

import kr.co.kyobo.vora.model.database.TagFollow;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TagFollowService {
    ResponseEntity<Object> tagFollow(TagFollow login, HttpServletResponse response);

    ResponseEntity<Object> tagsFollow(List<TagFollow> login, HttpServletResponse response);
}
