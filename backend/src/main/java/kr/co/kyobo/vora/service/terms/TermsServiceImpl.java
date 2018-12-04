package kr.co.kyobo.vora.service.terms;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import kr.co.kyobo.vora.model.database.Terms;
import kr.co.kyobo.vora.repository.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TermsServiceImpl implements TermsService {

    @Autowired
    private TermsRepository termsRepository;

    @Override
    public ResponseEntity<Object> findJoinTerms() {
        List<Terms> lastest = this.termsRepository.findJoinTerms();
        return ResponseEntityUtil.makeResultSuccess(lastest);
    }

    @Override
    public ResponseEntity<Object> getAllTerms(Terms terms) {
        List<Terms> lastest = this.termsRepository.findAllTerms(terms);
        return ResponseEntityUtil.makeResultSuccess(lastest);
    }
}
