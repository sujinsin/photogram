package com.cos.photogramstart.service;

import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.util.ImageUtil;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final S3Service s3Service;
	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	// @Transactional(propagation=Propagation.SUPPORTS, readOnly=true,
	// noRollbackFor=Exception.class)
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {

		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다. ");
		});

		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});

		UserProfileDto dto = new UserProfileDto();

		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setImageCount(userEntity.getImages().size());
		dto.setUser(userEntity);

		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {

		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 id 입니다.");
		});

		userEntity.setName(user.getName());
		String rawPassword = user.getPassword();
		String encPassword = bcryptPasswordEncoder.encode(rawPassword);

		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		return userEntity;
	}

	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {

		User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
			throw new CustomApiException("프로필변경 실패 : 해당 회원이 없습니다. ");
		});

		String imgPath = null;
		try {
			imgPath = s3Service.upload(profileImageFile, "user" + principalId);
			userEntity.setProfileImageUrl(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userEntity;
	}
}
