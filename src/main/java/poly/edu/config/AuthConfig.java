package poly.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import poly.edu.interceptor.AuthInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns(
//                        "/order/**",
//                        "/account/change-password",
//                        "/account/edit-profile"
//                )
//                .excludePathPatterns(
//                        "/auth/login",
//                        "/auth/logout",
//                        "/account/sign-up",
//                        "/account/forgot-password",
//                        "/css/**",
//                        "/js/**",
//                        "/images/**"
//                );
    }
}
