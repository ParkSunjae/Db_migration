package kr.co.kyobo.vora.service.businessCategory2;

import kr.co.kyobo.vora.model.database.BusinessCategory2;
import org.springframework.http.ResponseEntity;

public interface BusinessCategory2Service {
    ResponseEntity<Object> getListByParentIdx(BusinessCategory2 businessCategory2);
}
