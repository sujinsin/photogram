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

@ToString(exclude = "user") // 양방향 매핑시 계속해서 toString 을 반복해서 무시해줬음. 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	private String caption; 
	private String postImageUrl; 
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")  
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@JsonIgnoreProperties({"image"}) 
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;

	@Transient
	private boolean likeStatus;

	@Transient
	private int likeCount;
	
	// arraylist -> Collections.reverse() 로 가져온 리스트를 역순으로 뒤집을 수도 있음. 
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments; 

	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
