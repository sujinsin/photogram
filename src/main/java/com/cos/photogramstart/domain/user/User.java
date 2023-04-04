package com.cos.photogramstart.domain.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.common.RoleType;
import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 가입 유저 정보 엔티티
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Entity 
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// User 의 pk = id 
	// IDENTITY = 디비의 기본전략을 따라감
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	// 유저이름
	@Column(length = 100, unique = true) 
	private String username; 
	
	// 비밀번호 (NOT NULL)
	@Column(nullable = false)
	private String password;
	
	// 사용자 이름 (NOT NULL)
	@Column(nullable = false)
	private String name;
	
	private String website; // 웹사이트
	private String bio; // 자기소개
	
	// 이메일 (NOT NULL)
	@Column(nullable = false)
	private String email;
	
	private String phone;// 폰번호
	private String gender; // 성별

	private String profileImageUrl; // 대표프로필사진
	
	// @Enumerated JAVA에서 enum 타입 저장시 디비에 문자열 타입으로 저장하는 어노테이션
	@Enumerated(EnumType.STRING)
	private RoleType role; //권한
	
	// 1:N 의 관계 , 연관관계의 주인 = Image 엔티티의 user 필드(fk는 Image에 )
	// @JsonIgnoreProperties JSON 직렬화 시 toString의 User 필드 무한참조 방지를 위해 파싱시 무시하도록 설정
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) 
	private List<Image> images;
	
	// 유저의 가입 날짜
	private LocalDateTime createDate;
	
	//디비에 저장전에 현재시간을 먼저 저장하기위해 createDate() 메서드 먼저 실행하기 위한 설정 
	@PrePersist 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}