package com.cos.photogramstart.config;

//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@EnableOAuth2Client	
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	// https://velog.io/@yaho1024/Spring-Security-FilterChainProxy 프록시 관련 자세하게 

	//ioc
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
//	
//	@Bean
//	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring()
//	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//	}

	@Bean
	public DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception  {
		
		// 일단 csrf사용안하겠다..
		http.csrf().disable();
		
		
		// super 가 없으면 - 기존 시큐리티가 가지고 있는 기능이 비활성화 됨. login 디폴트사라짐. 

		http.authorizeRequests()
			.antMatchers("/", "/user/**","/image/**/","/subscribe/**","/comment/**","/api/**").authenticated()
			//.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
			.and() 
			.formLogin() 
			.loginPage("/auth/signin") 
			.loginProcessingUrl("/auth/signin") 
			.defaultSuccessUrl("/") 
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService);
		return http.build();
	}
	
	@Bean
	public HttpFirewall defaultHttFirewall() {
		return new DefaultHttpFirewall();
	}
}
