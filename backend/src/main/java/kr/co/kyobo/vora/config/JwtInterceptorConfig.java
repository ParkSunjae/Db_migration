package kr.co.kyobo.vora.config;

import kr.co.kyobo.vora.common.ParamConstants;
import kr.co.kyobo.vora.common.api.UriVersion;
import kr.co.kyobo.vora.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        개발시 주석처리
        registry.addInterceptor(jwtInterceptor()).addPathPatterns(UriVersion.API_VERSION_PRIVATE + ParamConstants.ALL_PATH);
    }

}
