package kr.co.kyobo.vora.messages;

import kr.co.kyobo.vora.model.api.ResponseEntityUtil;
import org.springframework.http.ResponseEntity;

public class ResultConstants {
    public static ResponseEntity<Object> invalidRequest(){
        return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_COMMON_INVALID_REQUEST.getCode());
    }

    public static  ResponseEntity<Object> unauthorized(){
        return ResponseEntityUtil.makeResultError(ErrorMessages.COMMON_UNAUTHORIZED_FAIL.getCode());
    }

    public static  ResponseEntity<Object> internalServerError(){
        return ResponseEntityUtil.makeResultError(ErrorMessages.SYSTEM_COMMON_FAIL.getCode());
    }

    public static  ResponseEntity<Object> loginFail(){
        return ResponseEntityUtil.makeResultError(ErrorMessages.COMMON_LOGIN_FAIL.getCode());
    }

    public static ResponseEntity<Object> checkResult(Object object) {
        return ResponseEntityUtil.makeResultSuccess(object);
    }
}
