package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

// 데이터만 리턴하는 컨트롤러로 만들꺼라 api 패키지에 넣어줬음. 

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
	
	private final SubscribeService subscribeService;
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {

		int fromUserId = principalDetails.getUser().getId();
		if(fromUserId != toUserId) {
			subscribeService.구독하기(fromUserId, toUserId); 
			return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null),HttpStatus.OK );			
		}else {
			return new ResponseEntity<>(new CMRespDto<>(-1,"자신을 구독할 수 없습니다",null),HttpStatus.BAD_REQUEST );		
		}
	}

	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unscubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int  toUserId) {
		int fromUserId = principalDetails.getUser().getId();
		subscribeService.구독취소(fromUserId, toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독취소하기 성공",null),HttpStatus.OK );
	}
}
