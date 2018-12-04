package kr.co.kyobo.vora.service.businessCategory1;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.repository.BusinessCategory1Repository;
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
public class BusinessCategory1ServiceImpl implements BusinessCategory1Service {

    @Autowired
    BusinessCategory1Repository businessCategory1Repository;

    @Override
    public ResponseEntity<Object> getList() {
        Map<String, Object> result = new HashMap<>();
        result.put("businessCategory1List",businessCategory1Repository.findAll());
        return ResponseEntityUtil.makeResultSuccess(result);
    }
}
