package kr.co.kyobo.vora.service.recommendation;

import kr.co.kyobo.vora.model.api.PersonalRecommendationPost;
import kr.co.kyobo.vora.model.api.PersonalRecommendationTag;
import kr.co.kyobo.vora.model.api.PersonalRecommendationUser;
import kr.co.kyobo.vora.model.database.Member;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface PersonalService {

    ResponseEntity<Object> tagRecommendPC(PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response);
    ResponseEntity<Object> tagRecommendMobile(PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response);

    ResponseEntity<Object> userRecommendPC(PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response);
    ResponseEntity<Object> userRecommendMobile(PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response);

    ResponseEntity<Object> postRecommend(PersonalRecommendationPost personalRecommendationPost, HttpServletResponse response);

}
