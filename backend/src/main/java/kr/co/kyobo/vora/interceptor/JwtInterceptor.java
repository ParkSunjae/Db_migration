package kr.co.kyobo.vora.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kyobo.vora.common.ParamConstants;
import kr.co.kyobo.vora.common.SecurityConstants;
import kr.co.kyobo.vora.model.api.ReturnObject;
import kr.co.kyobo.vora.model.jwt.JwtValidationResult;
import kr.co.kyobo.vora.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        if(request.getHeader(SecurityConstants.HEADER_STRING) == null){
            token = "";
        }else{
            token = request.getHeader(SecurityConstants.HEADER_STRING).replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        JwtValidationResult result = jwtService.validateToken(token);
        if(token != "" && result.getIsValid()){
            return true;
        }else {

            ReturnObject message = new ReturnObject(HttpStatus.UNAUTHORIZED.toString(),"권한이 없습니다.");
            response.setContentType(ParamConstants.APPLICATION_JSON);
            response.setCharacterEncoding(ParamConstants.LOCALE_CHAR_SET);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(message));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return false;
            //throw new UnauthorizedException();
        }
    }
}
