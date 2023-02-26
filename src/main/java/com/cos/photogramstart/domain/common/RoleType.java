package com.cos.photogramstart.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN, ROLE_USER");
	
	private String value;
}
