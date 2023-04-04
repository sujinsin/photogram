package com.cos.photogramstart.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType {
	
	/* USER : 기본 사용자 
	 * ADMIN : 관리자 페이지용 
	 * */
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN, ROLE_USER");
	
	private String value;
}
