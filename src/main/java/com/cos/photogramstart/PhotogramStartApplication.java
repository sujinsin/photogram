package com.cos.photogramstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhotogramStartApplication {
// extends SpringBootServletInitializer
	public static void main(String[] args) {
		SpringApplication.run(PhotogramStartApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(PhotogramStartApplication.class);
//	}
}
