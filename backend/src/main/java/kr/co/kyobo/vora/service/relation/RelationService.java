package kr.co.kyobo.vora.service.relation;

import kr.co.kyobo.vora.model.api.PersonagesRelatedToEachTagRequest;
import kr.co.kyobo.vora.model.api.PersonalRecommendationPost;
import kr.co.kyobo.vora.model.api.PersonalRecommendationTag;
import kr.co.kyobo.vora.model.api.PersonalRecommendationUser;
import kr.co.kyobo.vora.model.database.Tags;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface RelationService {
    ResponseEntity<Object> tagRelationTag(Tags tags, HttpServletResponse response);
    ResponseEntity<Object> tagRelationUser(PersonagesRelatedToEachTagRequest personagesRelatedToEachTagRequest, HttpServletResponse response);
}
