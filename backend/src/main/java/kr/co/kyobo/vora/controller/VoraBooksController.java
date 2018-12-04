package kr.co.kyobo.vora.controller;

import kr.co.kyobo.vora.model.api.NaverSearchReq;
import kr.co.kyobo.vora.service.vorabooks.VoraBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoraBooksController {

    @Autowired
    private VoraBooksService voraBooksService;

    @PostMapping("/api/v1/public/books/find")
    public ResponseEntity<Object> findbooks(@RequestBody NaverSearchReq naverSearchReq){
        return this.voraBooksService.findBookService(naverSearchReq);
    }
}
