package com.cos.photogramstart;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewControllerTest {
	
	@PostMapping("/oauth2/authorization/kakaotalk") 
	public void kakaoLogin(OAuth2UserRequest userRequest, DefaultOAuth2UserService ddd){
		System.out.println("로그인타냐? 컨트롤러 카카오 : "+userRequest);
		System.out.println("로그인타냐? 컨트롤러 카카오 : "+ddd);
	}
}
