package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.model.api.NaverLocationSearchReq;
import kr.co.kyobo.vora.model.api.NaverSearchReq;
import kr.co.kyobo.vora.service.naver.NaverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NaverApiController {

    @Autowired
    private NaverService naverService;

    @PostMapping("/api/v1/public/naver/movie")
    public ResponseEntity<Object> searchMovie(@RequestBody NaverSearchReq req){
        log.info("start======"+req.getStart());
        return this.naverService.searchMovie(req);
    }

    @PostMapping("/api/v1/public/naver/location")
    public ResponseEntity<Object> searchLocation(@RequestBody NaverLocationSearchReq naverLocationSearchReq) {
        return this.naverService.searchLocation(naverLocationSearchReq);
    }

}
