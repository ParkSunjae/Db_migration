package kr.co.kyobo.vora.service.jwt;

import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.jwt.JwtClaim;
import kr.co.kyobo.vora.model.jwt.JwtValidationResult;


public interface JwtService {
    //JWT 액세스 토큰 생성
    public String makeAccessToken(Account account);
    //JWT 토큰 유효성 체크
    public JwtValidationResult validateToken(String jwt);

    //JWT 토큰 파싱
    public JwtClaim parseToken(String jwt);

    //redis에 active token 등록
    //public void setActiveToken(String mbrNo, String jwtToken);

    //redis에서 active token 조회
    //public String getActiveToken(String mbrNo);

    //redis에서 active token 삭제
    //public void deleteActiveToken(String mbrNo);
}
