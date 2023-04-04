package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;
	
	// 내가 구독한 사람들의 게시물 이미지를 볼 수 있는 페이지로 이동, 메인페이지
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}

	// 이미지 인기페이지로 이동 , 좋아요가 많은 순으로 내림차순으로 이미지가 정렬되어있음. 
	@GetMapping("/image/popular")
	public String popular(Model model) {
		List<Image> images = imageService.인기사진();
		
		model.addAttribute("images",images);
		return "image/popular";
	}
	
	// 이미지 업로드 페이지로 이동 , 게시물 이미지 등록 페이지
	@GetMapping("/image/upload")
	public String uploadPage() {
		return "image/upload";
	}
	
	// 이미지 업로드 정보 전송 insert 
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
		}
		
		imageService.사진업로드(imageUploadDto, principalDetails);
		
		return "redirect:/user/" + principalDetails.getUser().getId();
	}
}
