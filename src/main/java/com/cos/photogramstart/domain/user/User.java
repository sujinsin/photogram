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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(length = 100, unique = true) 
	private String username; 
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	private String website; // 웹사이트
	private String bio; // 자기소개
	
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;

	private String profileImageUrl; // 프로필사진
	
	@Enumerated(EnumType.STRING)
	private RoleType role; //권한
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) 
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}