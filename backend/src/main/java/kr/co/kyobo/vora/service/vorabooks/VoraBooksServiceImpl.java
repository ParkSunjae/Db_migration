package kr.co.kyobo.vora.service.vorabooks;

import kr.co.kyobo.vora.model.api.NaverSearchReq;
import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.VoraBooks;
import kr.co.kyobo.vora.repository.VoraBooksRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class VoraBooksServiceImpl implements VoraBooksService {

    @Autowired
    private VoraBooksRespository voraBooksRespository;

    @Override
    public ResponseEntity<Object> findBookService(NaverSearchReq naverSearchReq) {

        naverSearchReq.setGetPage(naverSearchReq.getStart());

        List<VoraBooks> find = this.voraBooksRespository.findBooks(naverSearchReq);
        int totalCount = this.voraBooksRespository.findBooksTotalCount();

        HashMap<String, Object> returnObj = new HashMap<>();
        returnObj.put("page", naverSearchReq.getStart());
        returnObj.put("pagePerCount", naverSearchReq.getDisplay());
        returnObj.put("total", totalCount);
        returnObj.put("books", find);


        return ResponseEntityUtil.makeResultSuccess(returnObj);
    }
}
