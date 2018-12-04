package kr.co.kyobo.vora.model.api;

import kr.co.kyobo.vora.common.ParamConstants;
import kr.co.kyobo.vora.messages.ErrorMessages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    private static HttpHeaders makeConfig(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return responseHeaders;
    }

    public static ResponseEntity<Object> makeResultSuccess(Object obj){
        ReturnObject response = new ReturnObject(ParamConstants.UPPER_OK, "", obj);
        return new ResponseEntity<Object>(response, makeConfig(), HttpStatus.OK);
    }

    public static ResponseEntity<Object> makeResultError(String errorCode){
        ReturnObject response = new ReturnObject(errorCode, ErrorMessages.getMessage(errorCode), null);
        return new ResponseEntity<Object>(response, makeConfig(), HttpStatus.OK);
    }
}
