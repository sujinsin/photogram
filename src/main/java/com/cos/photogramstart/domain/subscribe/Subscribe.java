package com.cos.photogramstart.domain.subscribe;

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

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// fromUserId, toUserId 를 결합해 복합키를 만듦
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {	@UniqueConstraint(name = "subscribe_uk",columnNames = {"fromUserId", "toUserId"}	)}) 
public class Subscribe {
	
	// PK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/* object 따라 엔티티가 만들어질때 언더스코어 말고 카멜 표기법으로 변경  @JoinColumn(name = "toUserId")
	 * fromUserId으로 fk 속성명 지정
	 * N:1의 관계 : 기본 EAGER 전략 
	 */
	@JoinColumn(name = "fromUserId")
	@ManyToOne
	private User fromUser; // 구독한 사람

	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; // 구독 받은 사람
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
