package kr.co.kyobo.vora.service.tagFollow;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.TagFollow;
import kr.co.kyobo.vora.repository.TagFollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TagFollowServiceImpl implements TagFollowService{

    @Autowired
    TagFollowRepository tagFollowRepository;

    @Override
    public ResponseEntity<Object> tagFollow(TagFollow tagFollow, HttpServletResponse response){
        //단일 태그 팔로우
        //TODO : 토글기능 추가 필요 or 분기처리
        int success = tagFollowRepository.save(tagFollow);
        HashMap<String, Object> result = new HashMap<>();
        result.put("tagFollowSuccess", success);
        return ResponseEntityUtil.makeResultSuccess(result);
    }

    @Override
    public ResponseEntity<Object> tagsFollow(List<TagFollow> tagFollows, HttpServletResponse response){
        //태그목록 팔로우
        int success = tagFollowRepository.saveMulti(tagFollows);
        HashMap<String, Object> result = new HashMap<>();
        result.put("tagFollowSuccess", success);
        return ResponseEntityUtil.makeResultSuccess(result);
    }


}
