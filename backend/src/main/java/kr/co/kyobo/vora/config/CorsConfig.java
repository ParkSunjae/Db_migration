package kr.co.kyobo.vora.config;

import io.swagger.models.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static final String allowOrigins[] = {
            "http://localhost",
            "http://192.168.0.16",
            "http://127.0.0.1",
            "https://localhost",
            "http://13.209.190.247"
            };

    private boolean checkAllowOrigin(String origin){
        if(origin != null){
            for( int i=0 ; i  < allowOrigins.length ; i ++){
                if( origin.startsWith(allowOrigins[i]) ){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = ((HttpServletRequest) request).getHeader("Origin");
        if(origin != null){
            log.info("=========origin========" + origin);
        }


        if ( checkAllowOrigin(origin) ){
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");

        }
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //        response.addHeader("Access-Control-Allow-Origin", "https://app.passcoin.co.kr");
        //        response.addHeader("Access-Control-Allow-Origin", "https://test-app.passcoin.co.kr");
        //        response.addHeader("Access-Control-Allow-Origin", "https://localhost");
        if(request.getMethod().equals(HttpMethod.OPTIONS.name())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }else{
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }


}
