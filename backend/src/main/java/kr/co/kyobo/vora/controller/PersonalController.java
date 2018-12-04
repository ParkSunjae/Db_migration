package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.common.api.UriEntity;
import kr.co.kyobo.vora.common.api.UriMethod;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.model.api.PersonalRecommendationPost;
import kr.co.kyobo.vora.model.api.PersonalRecommendationTag;
import kr.co.kyobo.vora.model.api.PersonalRecommendationUser;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.service.recommendation.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PersonalController {
    @Autowired
    PersonalService personalService;

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.PERSONAL_RECOMMEND + UriMethod.POST_RECOMMEND)
    public ResponseEntity<Object> postRercommend(@RequestBody PersonalRecommendationPost personalRecommendationPost, HttpServletResponse response){
        return personalService.postRecommend(personalRecommendationPost,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.PERSONAL_RECOMMEND + UriMethod.TAG_RECOMMEND_MOBILE)
    public ResponseEntity<Object> tagRecommendMobile(@RequestBody PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response){
        return personalService.tagRecommendMobile(personalRecommendationTag,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.PERSONAL_RECOMMEND + UriMethod.TAG_RECOMMEND_PC)
    public ResponseEntity<Object> tagRecommendPC(@RequestBody PersonalRecommendationTag personalRecommendationTag, HttpServletResponse response){
        return personalService.tagRecommendPC(personalRecommendationTag,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.PERSONAL_RECOMMEND + UriMethod.USER_RECOMMEND_MOBILE)
    public ResponseEntity<Object> userRecommendMobile(@RequestBody PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response){
        return personalService.userRecommendMobile(personalRecommendationUser,response);
    }

    @PostMapping(UriVersion.API_VERSION_PRIVATE + UriEntity.PERSONAL_RECOMMEND + UriMethod.USER_RECOMMEND_PC)
    public ResponseEntity<Object> userRecommendPC(@RequestBody PersonalRecommendationUser personalRecommendationUser, HttpServletResponse response){
        return personalService.userRecommendPC(personalRecommendationUser,response);
    }
}
