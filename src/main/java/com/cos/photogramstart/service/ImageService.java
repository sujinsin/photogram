package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.likes.LikesRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ImageService {
	
//	private final S3Service s3Service;
	private final ImageRepository imageRepository;
	
	private final LikesRepository likesRepository;
	
	@Transactional(readOnly=true)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		// images 에 좋아요 상태를 담아야함. // 양방향 매핑으로 가지고 올 수 있게 필드 수정 
		images.forEach((image) -> {
			
			image.setLikeCount(image.getLikes().size());

			image.getLikes().forEach((like) -> {
				if(like.getUser().getId() == principalId) { 
					image.setLikeStatus(true);
				}
			});
		});
		return images;
	}

	@Value("${file.path}")
	private String uploadFolder; 
	
	@Transactional 
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

		UUID uuid =UUID.randomUUID();
		
		String imageFileName = uuid + "_" +imageUploadDto.getFile().getOriginalFilename(); 
	
		Path imageFilePath = Paths.get("file:///" + uploadFolder + imageFileName);
		
		try {
			Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
			Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
			imageRepository.save(image);	
		} catch (Exception e) {
			e.printStackTrace();
		}

//		String imgPath = null;
//		try {
//			imgPath = s3Service.upload(imageUploadDto.getFile(), "user" + principalDetails.getUser().getId());
//			//userEntity.setProfileImageUrl(imgPath);
//			Image image = imageUploadDto.toEntity(imgPath, principalDetails.getUser());
//			imageRepository.save(image);		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
	}

	@Transactional
	public List<Image> 인기사진() {
		
		return imageRepository.mPopular();
	}
}
