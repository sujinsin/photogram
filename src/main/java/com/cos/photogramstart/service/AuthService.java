package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.common.RoleType;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 1. Ioc 2. 트랜잭션관리  
public class AuthService {

	private final UserRepository userRepository;
	
	// 스프링 시큐리티 로그인을 위한 비밀번호 암호화 
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원가입(User user) {

		// rawPassword 평문비밀번호 , encPassword 암호화한 비밀번호 
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		// 다시 User 객체에 삽입
		user.setPassword(encPassword); 
		user.setRole(RoleType.USER); 
		
		// jpa 기본 crud save를 사용해 디비에 insert 
		User userEntity = userRepository.save(user); 
		
		return userEntity;
		
	}
}
