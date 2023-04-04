package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
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
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략을 데이터베이스를 따라감. 
	private int id;
	
	// @Column 일반컬럼 제한 조건 추가 , 길이 100, NOT NULL  
	@Column(length= 100, nullable=false)
	private String content; // 댓글
	
	/*User 의  images 필드 json 파싱 무시 (무한참조 방지)
	 * @JoinColumn : User 의 userId pk 필드 참조 , fk 속성 이름을 userId 로 설정.
	 *  N:1 의 관계 : 기본전략 무조건 같이 가져오는 eager 전략 
	 * */
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user; // 댓글 작성 유저의 정보
	
	/*
	 * @JoinColumn : Image 의 imageId pk 참조 , fk 속성 이름을 imageId 로 설정.
	 * N:1 : EAGER 기본전략 
	 * */
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image; // 댓글이 달린 이미지
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now(); // 댓글작성시간
	}
}
