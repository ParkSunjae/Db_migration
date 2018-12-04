package kr.co.kyobo.vora.service.vorabooks;

import kr.co.kyobo.vora.model.api.NaverSearchReq;
import org.springframework.http.ResponseEntity;

public interface VoraBooksService {
    ResponseEntity<Object> findBookService(NaverSearchReq naverSearchReq);
}
