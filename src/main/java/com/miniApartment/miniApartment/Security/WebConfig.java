//package com.miniApartment.miniApartment.Security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(url)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders(url)
//                .allowCredentials(true);
//    }
//    private static final String[] url = {"/auth/welcome"
//            , "/api/file/upload"
//            , "/api/tenants/**"
//            , "/auth/**"
//            , "/api/room/**"
//            , "/api/payment/**"
//            , "/api/expenses/**"
//            , "/api/user/**"
//            , "/mail/**"
//            , "/contract/**"
//            ,"/swagger-ui/**"
//            , "/v3/api-docs/**"
//    };
//}
