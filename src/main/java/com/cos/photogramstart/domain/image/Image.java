package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 이미지 저장 엔티티
@ToString(exclude = "user") // 양방향 매핑시 계속해서 toString 을 반복해서 무시해줬음. 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Entity
public class Image {

	// pk , 디비 기본전략 auto_increment 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	// 이미지 캡션
	private String caption;
	
	// 이미지 경로
	private String postImageUrl; 
	
	
	/*@JsonIgnoreProperties : json 파싱시 무한참조 방지 User 객체의 images필드 무시
      @JoinColumn : 외래키 컬럼명 지정 , user 필드가 참조하는 컬럼명은 User 객체의 userId임
	  @ManyToOne : N:1의 관계, 연관관계의 주인 FK를 가짐 가져오는 정보가
	   				1개이기 때문에 기본전략 EAGER임(함께 가져옴)*/
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId") 
	@ManyToOne(fetch = FetchType.EAGER)
	private User user; // 이미지의 주인 유저의 정보

	/*@JsonIgnoreProperties : json 파싱시 무한참조방지
	  @OneToMany : 1:N의 관계, Likes 의 image가 연관관계의 주인이다.*/
	@JsonIgnoreProperties({"image"}) 
	@OneToMany(mappedBy = "image") 
	private List<Likes> likes;// 이미지의 좋아요 정보 

	
	/* likeStatus : 내가 해당 이미지를 좋아요 했는지 여부 
	   @Transient : 디비에 저장하지 않는 필드 선언, 객체에서만 사용*/
	@Transient
	private boolean likeStatus;

	// likeCount : 현재 이미지의 좋아요 총 개수
	@Transient
	private int likeCount;
	
	/* arraylist -> Collections.reverse() 로 가져온 리스트를 역순으로 뒤집을 수도 있음.
		@OrderBy : 디비에서 가져올때 객체로 내림차순정렬 후 가져옴 
		@JsonIgnoreProperties : json 파싱시 무한참조 방지 
		@OneToMany : 1:N 관계 , Comment 의 image 필드가 fk이다(연관관계주인).*/
	@OrderBy("id DESC") 
	@JsonIgnoreProperties({"image"}) 
	@OneToMany(mappedBy = "image") 
	private List<Comment> comments; // 댓글정보

	private LocalDateTime createDate; // 이미지업로드시간
	
	// 테이블 생성 전 먼저 실행 후 createDate 시간 데이터 저장 후 디비에 전송
	@PrePersist
	public void createDate() {
		// java.time 패키지에있는 클래스, 현재 컴퓨터의 시간정보 저장 
		this.createDate = LocalDateTime.now();
	}
}
