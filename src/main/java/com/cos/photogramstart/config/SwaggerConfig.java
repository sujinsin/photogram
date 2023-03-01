package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // Swagger에 의해 노출된 끝점을 제어하는 ​​방법을 제공하는 ApiSelectorBuilder 인스턴스를 반환
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()) // RequestHandlerSelectors와 PathSelectors를 사용하여 API의 노출 여부를 결정 , any()를 사용하면 Swagger를 통해 전체 API에 대한 문서를 사용
                .build();
    }
    
    //
    // Docket : Swagger 설정을 담당하는 클래스 
    // select() : 메소드로 api 문서화 대상을 지정할 수 있고
    // apis() , paths() : 메소드로 api 문서화 대상을 제한 
    // http://localhost:8080/swagger-ui.html 접속해 Swagger ui 확인가능
}