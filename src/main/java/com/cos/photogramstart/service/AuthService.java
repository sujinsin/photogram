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
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원가입(User user) {

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword); 
		
		System.out.println("권한 서비스단에서 뭘 뽑아서 주는거야? " +  user.getRole());

		user.setRole(RoleType.USER); 
		System.out.println("권한 서비스단에서 뭘 뽑아서 주는거야? 넣고 나서  " +  user.getRole());
		
		User userEntity = userRepository.save(user); 
		return userEntity;
		
	}
}
