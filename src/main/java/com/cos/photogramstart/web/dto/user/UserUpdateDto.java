package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

// 업데이트에서 제외되는 필드들이 없는 dto를 생성 
@Data
public class UserUpdateDto {
	
	@NotBlank // 필수
	@Size(min=2, max=20)
	private String name;
	
	@NotBlank
	private String password; 

	private String website; 
	private String bio;
	private String phone;
	private String gender;
	
	public User toEntity() {
		return User.builder()
				.name(name) 
				.password(password) 
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
