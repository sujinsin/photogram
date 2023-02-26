package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 오버로딩 사용  // attributes 페이스북 로그인지 정보를 맵으로 받아옴 
	public PrincipalDetails(User user, Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	
	//권한을 가져오는 함수. 한개가 아닐 수 있음 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<>();
		// 왜 ArrayList를 ? 얘의 부모가 collection이라. 
		
		collector.add(() -> {return user.getRole().name();});
		// 람다식을 쓰는 이유? add 안에 함수를 넣는게 목적 자바는 일급객체가 아니므로 매개변수에 함수를 못넣음 그래서 람다식으로 함수를 넘겨줬음. 

		return collector;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// true 가 되어야 정상적인 로그인이 된다. 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	// 계정이 락 잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 니 비밀번호 유효기간 만료? 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 니 계정이 비활성화 여부
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// OAuth2User 구현시 추가 2개 더 생김 아래 함수 getAttributes, getName
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
}
