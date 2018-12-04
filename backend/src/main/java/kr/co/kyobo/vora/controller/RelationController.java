package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.api.PersonagesRelatedToEachTagRequest;
import kr.co.kyobo.vora.model.database.Tags;
import kr.co.kyobo.vora.service.relation.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RelationController {
    @Autowired
    private RelationService relationService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.RELATION + UriMethod.GET_RELATED_TAG)
    public ResponseEntity<Object> getRelatedTag(@RequestBody Tags tags, HttpServletResponse response){
        return relationService.tagRelationTag(tags, response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.RELATION + UriMethod.GET_RELATED_USER)
    public ResponseEntity<Object> getRelatedUser(@RequestBody PersonagesRelatedToEachTagRequest personagesRelatedToEachTagRequest,
                                                 HttpServletResponse response){
        return relationService.tagRelationUser(personagesRelatedToEachTagRequest, response);
    }
}
