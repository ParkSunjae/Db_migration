package kr.co.kyobo.vora.service.businessCategory2;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.BusinessCategory2;
import kr.co.kyobo.vora.repository.BusinessCategory2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class BusinessCategory2ServiceImpl implements BusinessCategory2Service {

    @Autowired
    BusinessCategory2Repository businessCategory2Repository;

    @Override
    public ResponseEntity<Object> getListByParentIdx(BusinessCategory2 businessCategory2) {
        Map<String, Object> result = new HashMap<>();
        result.put("businessCategory2List",businessCategory2Repository.findByParentIdx(businessCategory2));
        return ResponseEntityUtil.makeResultSuccess(result);
    }
}
