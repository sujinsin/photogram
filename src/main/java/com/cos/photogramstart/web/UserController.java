package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	// 회원 프로필페이지로 이동 
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		UserProfileDto dto = userService.회원프로필(pageUserId,principalDetails.getUser().getId());
		model.addAttribute("dto",dto); 
		
		return "/user/profile";
	}
	
	// 회원 정보 업데이트 페이지로 이동
	@GetMapping("/user/{id}/update")
	public String updatePage(@PathVariable int id	, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(id == principalDetails.getUser().getId()) {
			return "user/update";			
		}else {
			throw new CustomException("회원수정 비정상접근 : 페이지주인이 아닙니다. ");
		}
	}
}
