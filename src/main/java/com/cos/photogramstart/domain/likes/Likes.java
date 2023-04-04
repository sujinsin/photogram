package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name = "likes_uk", 
						columnNames = {"imageId", "userId"} 
				)
		}
)// 테이블 생성시 여러개의 제약조건 설정 제약조건  (imageId, userId)/(likes_uk 제약조건이름)
public class Likes {
	
	//pk , auto_increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/*
	 * @JoinColumn : 연관관계의 주인이 되는 엔티티에서 사용하는 컬럼
	 * @ManyToOne : n:1로 , 연관관계의 주인이 됨, 기본전략 EAGER
	 * */
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image; // 좋아요들을 받은 이미지
	
	/*
	 * @JsonIgnoreProperties : 무한참조 방지, json 파싱시 무시
	 * @JoinColumn : 연관관계의 주인인 나와 User 객체의 userId pk와 연결 
	 * @ManyToOne : N:1 관계, EAGER 전략*/
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 좋아요한 유저의 정보
	
	// 좋아요를 누른 시간 
	private LocalDateTime createDate;
	
	// 디비 저장전 메서드 호출 후 시간 저장
	@PrePersist 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
