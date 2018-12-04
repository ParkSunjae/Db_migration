package kr.co.kyobo.vora.service.terms;


import kr.co.kyobo.vora.model.database.Terms;
import org.springframework.http.ResponseEntity;

public interface TermsService {

    ResponseEntity<Object> findJoinTerms();

    ResponseEntity<Object> getAllTerms(Terms terms);

}
