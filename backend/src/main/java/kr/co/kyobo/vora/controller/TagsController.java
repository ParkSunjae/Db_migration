package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.api.SearchTagList;
import kr.co.kyobo.vora.model.database.TagFollow;
import kr.co.kyobo.vora.service.tags.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAGS + UriMethod.CALL_TAGS)
    public ResponseEntity<Object> get100Tags(@RequestBody SearchTagList searchTagList){
        return this.tagsService.getSuggestTags(searchTagList);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.TAGS + UriMethod.SAVE_USERS_TAGS)
    public ResponseEntity<Object> saveUsersTags(@RequestBody List<TagFollow> tagFollows){
        return this.tagsService.saveUsersTags(tagFollows);
    }
}
