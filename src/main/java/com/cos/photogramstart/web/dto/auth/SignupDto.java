package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;


@Data
public class SignupDto {
	
	//@Max(20) 데이터를 맞게 입력해도 자꾸20이하여야한다는 오류띄움. 잘 안먹는듯? 대신  Size로 사용
	@Size(min=4,max=20) 
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank 
	private String email;
	
	@NotBlank 
	private String name;
	
	// User 오브젝트를 반환하는 함수. 
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
