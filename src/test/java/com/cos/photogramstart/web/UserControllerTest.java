package com.cos.photogramstart.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Model model;
	
	@Mock
	private User user;
	
	@Mock
	private PrincipalDetails principalDetails;
	
	@Test	
	public void profileTest() {
		
		// 현재 사용자가 위치한 화면 페이지의 주인 id 를 가져온다.
		int pageUserId = 1;
		
		// 현재 사용자의 로그인 객체에서 id 값을 가져온다.
		int principalDetailsId = 2;
		
		// 임의로 UserProfileDto의 필드값을 집어넣는다.
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setPageOwnerState(true);
		userProfileDto.setImageCount(10);
		userProfileDto.setSubscribeState(false);
		userProfileDto.setSubscribeCount(5);
		userProfileDto.setUser(user);
		
		// principalDetails.getUser() 호출시 user 객체를 리턴해줌
	    given(principalDetails.getUser()).willReturn(user);
		
	    //principalDetails.getUser().getId() 호출시 principalDetailsId 값을 리턴해줌
		given(principalDetails.getUser().getId()).willReturn(principalDetailsId);
		
		//userService.회원프로필(pageUserId, principalDetailsId) 메서드 호출시 userProfileDto 객체 리턴해줌
	    given(userService.회원프로필(pageUserId, principalDetailsId)).willReturn(userProfileDto);
	    
		// 실제 테스트를 위해 실행
		String viewName = userController.profile(pageUserId, model, principalDetails);
		
		// mock 객체 메서드 호출 검증
		verify(userService).회원프로필(pageUserId, principalDetailsId);
		verify(model).addAttribute(eq("dto"), eq(userProfileDto));
		
		// 반환된 viewName 검증
		assertEquals("/user/profile", viewName);
	}
}
