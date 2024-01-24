package com.community.communityboard.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    public static final String API_TITLE = "커뮤니티 게시판";
    public static final String API_DESCRIPTION = "커뮤니티 게시판 프로젝트 API 명세서 입니다.";
    public static final String API_VERSION = "v2.0";


    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION)
                );
    }
}
