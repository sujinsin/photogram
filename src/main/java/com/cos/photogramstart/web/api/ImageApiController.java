package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	
	private final ImageService imageService;
	
	private final LikesService likesService;
	
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size=3) Pageable pageable ) {
		Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(),pageable);
		// 댓글 가져와야 하는데, Image 오브젝트에 댓글 가져오기 위해 양방향 매핑을 해준다. 
		
		return new ResponseEntity<>(new CMRespDto<>(1, "이미지를 가져왔어유 ", images), HttpStatus.OK);
	}
	
	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		//		좋아요 카운트를 위한 코드 
		Image image = likesService.좋아요(imageId,principalDetails.getUser().getId());
		image.setLikeCount(image.getLikes().size());
		
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요성공", image.getLikeCount()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> unlikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		//		좋아요 카운트를 위한 코드 
		Image image =likesService.좋아요취소(imageId,principalDetails.getUser().getId());
		image.setLikeCount(image.getLikes().size());
		
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요취소성공", image.getLikeCount()), HttpStatus.OK);
	}
	
}
