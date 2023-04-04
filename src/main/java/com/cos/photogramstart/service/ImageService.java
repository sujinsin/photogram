package com.cos.photogramstart.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final S3Service s3Service;
	private final ImageRepository imageRepository;

//	private final LikesRepository likesRepository;

	// 좋아요 상태가 계속 바뀌기 때문에 select 할때 마다 좋아요 정보를 담아줌
	@Transactional(readOnly = true)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId, pageable);

		// images 에 좋아요 상태를 담아야함. // 양방향 매핑으로 가지고 올 수 있게 필드 수정
		images.forEach((image) -> {

			// 좋아요 수 
			image.setLikeCount(image.getLikes().size());

			// 이미지의 좋아요에서 현재 로그인 사용자가 좋아요를 했는지 여부를 확인 후 했으면 상태 필드를 true 로 
			image.getLikes().forEach((like) -> {
				if (like.getUser().getId() == principalId) {
					image.setLikeStatus(true);
				}
			});
		});
		return images;
	}

	// s3 에 사진 저장 
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

		try {
			String imgPath = s3Service.upload(imageUploadDto.getFile(), "user" + principalDetails.getUser().getId());
			// userEntity.setProfileImageUrl(imgPath);
			Image image = imageUploadDto.toEntity(imgPath, principalDetails.getUser());
			imageRepository.save(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Transactional
	public List<Image> 인기사진() {

		return imageRepository.mPopular();
	}
}
