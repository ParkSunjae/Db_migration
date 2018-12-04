package kr.co.kyobo.vora.service.jwt;

import io.jsonwebtoken.*;
import kr.co.kyobo.vora.common.ParamConstants;
import kr.co.kyobo.vora.model.database.Account;
import kr.co.kyobo.vora.model.jwt.JwtClaim;
import kr.co.kyobo.vora.model.jwt.JwtValidationResult;
import kr.co.kyobo.vora.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static kr.co.kyobo.vora.common.SecurityConstants.EXPIRATION_SECONDES;
import static kr.co.kyobo.vora.common.SecurityConstants.SECRET;


@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public String makeAccessToken(Account account) {

        String token = null;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expDate = new Date(nowMillis + ( EXPIRATION_SECONDES * 1000 ));
        token = Jwts.builder()
                .setHeaderParam(ParamConstants.ALGS, ParamConstants.HS512)
                .setHeaderParam(ParamConstants.TYP, ParamConstants.JWT)
                .setIssuedAt(now)
                .setSubject(ParamConstants.USER)
                .setIssuer(ParamConstants.VORA)

                .claim(ParamConstants.DEVICE_TYPE, account.getDeviceType())
                .claim(ParamConstants.ACCOUNT_TYPE, account.getAccountType())
                .claim(ParamConstants.ACCOUNT_IDX, account.getIdx())
                .claim(ParamConstants.MEMBER_IDX, account.getUserIdx())

                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, this.generateKey())
                .compact();
        return token;
    }

    @Override
    public JwtValidationResult validateToken(String accessToken) {
        JwtValidationResult result = new JwtValidationResult();
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(accessToken);
            result.setIsValid(true);
            result.setMessage(ParamConstants.OK);
        }catch(JwtException e){
            result.setIsValid(false);
            if(e instanceof ExpiredJwtException){
                //JWT 권한claim 검사가 실패했을 때
                result.setMessage("expired jwt exception");
            }else if(e instanceof ClaimJwtException){
                //유효 기간이 지난 JWT를 수신한 경우
                result.setMessage("claim jwt exception");
            }else if(e instanceof MalformedJwtException){
                //구조적인 문제가 있는 JWT인 경우
                result.setMessage("malformed jwt exception");
            }
            else if(e instanceof SignatureException){
                // 접근이 허용되기 전인 JWT가 수신된 경우
                result.setMessage("signature exception");
            }
            else if(e instanceof UnsupportedJwtException){
                //시그너처 연산이 실패하였거나, JWT의 시그너처 검증이 실패한 경우
                result.setMessage("unsupported jwt exception");
            }
            else{
                //기타
                result.setMessage(e.getMessage());
            }
            //result.setException(e);
        }
        return result;
    }

    public JwtClaim parseToken(String accessToken){
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(accessToken);
        JwtClaim claim = new JwtClaim();

        claim.setDt((String) claims.getBody().get(ParamConstants.DEVICE_TYPE));
        claim.setMidx(Integer.toString((Integer) claims.getBody().get(ParamConstants.MEMBER_IDX)));
        claim.setAt((String) claims.getBody().get(ParamConstants.ACCOUNT_TYPE));
        claim.setAidx(Integer.toString((Integer) claims.getBody().get(ParamConstants.ACCOUNT_IDX)));
        return claim;
    }

    private byte[] generateKey() {
        byte[] apiKeySecretBytes = new byte[0];
        try {
            apiKeySecretBytes = SECRET.getBytes(ParamConstants.LOCALE_CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return apiKeySecretBytes;
    }


}
