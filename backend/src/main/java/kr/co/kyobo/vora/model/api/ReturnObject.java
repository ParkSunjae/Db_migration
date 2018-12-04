package kr.co.kyobo.vora.model.api;

import lombok.Data;

@Data
public class ReturnObject {
    private String code;
    private String message;
    private Object returnData;

    public ReturnObject() {

    }

    public ReturnObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnObject(String code, String message, Object returnData) {
        this.code = code;
        this.message = message;
        this.returnData = returnData;
    }
}
