package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId ) {
		Image image = new Image();
		image.setId(imageId);
		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			throw new CustomApiException("댓글등록 실패 : 유저 아이디가 없습니다. ");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setUser(userEntity);
		comment.setImage(image);
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void 댓글삭제(int commentId, int principalId) {
		try {
			
			Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
				throw new CustomApiException("해당 아이디가 존재하지 않습니다. ");
			});
			
			if(comment.getUser().getId() == principalId ) {
				commentRepository.deleteById(commentId);						
			}else {
				throw new CustomApiException("해당 유저가 아닙니다. 비정상 접근중... ");
			}
	
		}catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}

	}
}
