package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	
	private boolean pageOwnerState; //state 가 붙으면 boolean 
	private int imageCount;// 게시물개수 , 뷰단에서 최종연산을 수행하지 않게, 서버에서 미리 연산하여 넘겨줌
	private boolean subscribeState; // 로그인유저가 현재 프로필 유저를 구독을 했는지 여부를 알기위해
	private int subscribeCount; // 현재 프로필페이지 주인을 구독한 구독자 수
	private User user; // 프로필의 주인 user 정보
	
}
