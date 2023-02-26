package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
 
	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	
	private final EntityManager em;
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT s.fromUserId id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = s.fromUserId), 1, 0) subscribeStatus, ");
		sb.append("if((? = s.fromUserId),1,0) equalUserState  ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("on u.id = s.fromUserId  ");
		sb.append("WHERE s.toUserId = ? "); 
		
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId); 

		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		System.out.println("subscribeDtos 를 출력해 : " + subscribeDtos);
		
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		userRepository.findById(toUserId).orElseThrow(() -> {
			throw new UsernameNotFoundException("구독하고자 하는 유저가 존재하지 않습니다. ");
		});
		
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);	
		}catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}


	
}
