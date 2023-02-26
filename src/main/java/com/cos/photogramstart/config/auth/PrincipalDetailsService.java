package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{

	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 디비에 해당 유저가 있는지 없는지 비교해줘야함. (비밀번호는 알아서 비교해줌)
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) 	
			return null;
		else
			return new PrincipalDetails(userEntity); 
			// 이 타입이 UserDetails 에 담겨서 리턴됨. 
			// 얘가 저장이 되니까 안에 들어있는 User 오브젝트를 세션에서 활용 할 수 있게 됨. 
	}
	
}
