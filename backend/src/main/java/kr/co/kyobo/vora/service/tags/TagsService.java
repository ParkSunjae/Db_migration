package kr.co.kyobo.vora.service.tags;

import kr.co.kyobo.vora.model.api.SearchTagList;
import kr.co.kyobo.vora.model.database.TagFollow;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagsService {
    ResponseEntity<Object> getSuggestTags(SearchTagList searchTagList);

    ResponseEntity<Object> saveUsersTags(List<TagFollow> tagFollows);
}
