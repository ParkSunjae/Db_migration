package kr.co.kyobo.vora.model.jwt;

import lombok.Data;

@Data
public class JwtValidationResult {
    Boolean isValid;
    String message;

}
