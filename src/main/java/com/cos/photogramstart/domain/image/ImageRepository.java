package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image,Integer>{
	@Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable);
	// 다른 버전을 사용할때 네이티브쿼리에 에러가 남 
	//Page<Image> mStory(@Param("principalId") int principalId, Pageable pageable);
	// 파람을 같이 적어줘야한다. 
	// 다른 네이티브 쿼리들도 적용해줘야함 
	
	@Query(value="SELECT * FROM image i LEFT outer JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId) c ON i.id = c. imageId ORDER BY likeCount IS NULL ASC,likeCount DESC,createDate IS NULL ASC ,createDate desc;", nativeQuery = true)
	List<Image> mPopular();
}
