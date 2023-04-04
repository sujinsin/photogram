package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor 
@Controller
public class AuthController {
	

	private final AuthService authService;
	
	// 로그인 페이지 
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	// 회원가입 페이지로 이동 
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	 
	// 회원가입 정보 저장 
	// BindingResult 유효성 검사에서 발생한 오류를 담는 객체
	//  @Valid : SignupDto 의 유효성 검사 어노테이션이 달린 필드들을 검사하기위해 달아주는 어노테이션
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { 

			log.info("signupDto = {}",signupDto.toString());
			
			User user = signupDto.toEntity();
			
			log.info(user.toString());
			
			// 회원가입 서비스로 
			authService.회원가입(user);
			
			return "auth/signin";		
	}
}
