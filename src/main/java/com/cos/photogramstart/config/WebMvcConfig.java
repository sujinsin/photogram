package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@PropertySource("classpath:/application.yml")
@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer{

//	@Value("${file.path}")
//	private String uploadFolder ;
//	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//		
//		registry
//			.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/** 이런 주소 패턴이 나오면 발동한다. 
//			.addResourceLocations("file:///" + uploadFolder) // 얘가 발동한다.  uploadFolder = E:/module/serverfiles/workspace/springBootwork2/upload/
//			.setCachePeriod(60*10*6) // 60 초 * 10 = 10분 * 6 = 1시간 동안 이미지 캐싱한다. 
//			.resourceChain(true)
//			.addResolver(new PathResourceResolver());
//        registry.
//        	addResourceHandler("/css/**", "/images/**", "/js/**")
//        .addResourceLocations("classpath:/static/");
//	}
 	 
 
}
