package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

// Multipart 타입에는 유효성검사 어노테이션 @NotBlank 등이 지원이 안됨
@Data
public class ImageUploadDto {
	
	public MultipartFile file; // 파일을 담아주기위해. 
	
	public String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl) 
				.user(user)// 사진을 저장한 유저 정보가 필요함
				.build();
	}
}
