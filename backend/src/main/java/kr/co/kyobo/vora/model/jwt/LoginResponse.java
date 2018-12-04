package kr.co.kyobo.vora.model.jwt;

import kr.co.kyobo.vora.model.database.MemberLoginHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private int mIdx;
    private int aIdx;
    private String deviceType;
    private String accountType;
    private String accessToken;
    private MemberLoginHistory lastLogin;
}
