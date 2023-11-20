package com.omtbp.movieservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Online Movie Ticket Booking",
                "This project is developed by Chandan",
                "1.0",
                "Terms of Service: (Effective Date: Nov 1, 2023)",
                new Contact("Chandan Sharma", "https://www.linkedin.com/in/chandan-pr-sharma", "chandan.pr.sharma@gmail.com"),
                "License of APIs",
                "Copyright@2023",
                Collections.emptyList()
                );
    }
}
