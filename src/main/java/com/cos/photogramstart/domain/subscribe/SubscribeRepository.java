package com.cos.photogramstart.domain.subscribe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer>{

	// 데이터베이스를 변경을 주는 native 쿼리에는 @Modifying을 줘야한다. / insert,delete, update 
	@Modifying
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId); // 성공1, 실패-1 리턴값, 0 오류는 아니지만 변경된 값이 없다. 

	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId); // insert10건은 변경된 행의 개수 10이 리턴됨. 
	

	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId); 
	
	@Query( value = "SELECT COUNT(*) FROM subscribe WHERE toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId); 
	
}
