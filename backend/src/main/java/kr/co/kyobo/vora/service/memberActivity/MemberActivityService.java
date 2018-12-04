package kr.co.kyobo.vora.service.memberActivity;

import kr.co.kyobo.vora.model.api.RequestParam;
import org.springframework.http.ResponseEntity;

public interface MemberActivityService {
    ResponseEntity<Object> getActionList(RequestParam requestParam);
    ResponseEntity<Object> getLoginLogList(RequestParam requestParam);
}
