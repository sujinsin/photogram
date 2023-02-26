package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.likes.LikesRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	
	private final ImageRepository imageRepository;
	
	
	@Transactional
	public Image 좋아요(int imageId, int principalId) {
		likesRepository.mLikes(imageId, principalId);
		Image image = imageRepository.findById(imageId).orElseThrow(() ->{
			throw new CustomApiException("해당 이미지를 찾을 수 업습니다.");
		});
		return image;
		
	}

	@Transactional
	public Image 좋아요취소(int imageId, int principalId) {
		likesRepository.mUnLikes(imageId, principalId);
		Image image = imageRepository.findById(imageId).orElseThrow(() ->{
			throw new CustomApiException("해당 이미지를 찾을 수 업습니다.");
		});
		return image;
	}
}
