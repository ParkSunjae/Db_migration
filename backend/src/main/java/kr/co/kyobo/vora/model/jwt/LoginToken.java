package kr.co.kyobo.vora.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    private String accessToken;
    private String dt;   //deviceType
    private String mIdx;   //memberIdx
    private String aIdx;   //accountIdx
    private String at;   //accountType
}
