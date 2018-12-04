package kr.co.kyobo.vora.config;

import kr.co.kyobo.vora.common.ParamConstants;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
public class SwaggerUiConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ParamConstants.SWAGGER_PATTERN)
                .addResourceLocations(ParamConstants.SWAGGER_LOCATION);

        registry.addResourceHandler(ParamConstants.WEBJAR_PATTERN)
                .addResourceLocations(ParamConstants.WEBJAR_LOCATION);
    }

}
