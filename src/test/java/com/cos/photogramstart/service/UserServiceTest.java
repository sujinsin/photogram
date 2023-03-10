package com.cos.photogramstart.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 테스트")
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private SubscribeRepository subscribeRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
     
	@Nested
	@DisplayName("회원프로필 메소드는")
	class Describe_회원프로필 {
		private final int pageUserId = 1;
		private final int principalId = 2;
		
		@Mock
		private User userEntity= User.builder().id(pageUserId).build();
		private final Image image1 = Image.builder().caption("이미지1").likeCount(3)
				.postImageUrl("https://localhost/images/image1.png").build();
		private final Image image2 = Image.builder().caption("이미지2").likeCount(5)
				.postImageUrl("https://localhost/images/image2.png").build();

		@BeforeEach
		void setUp() {
			userEntity.setImages(Stream.of(image1, image2).collect(Collectors.toList()));
		}

		@Test
		@DisplayName("페이지 사용자가 존재하지 않을 경우 예외를 던진다.")
		void it_throws_custom_exception_when_page_user_does_not_exist() {
			
			// given
			when(userRepository.findById(pageUserId)).thenReturn(Optional.empty());
			// Optional.empty() 반환시 CustomException 예외가 발생한다.

			// when
			Assertions.assertThrows(CustomException.class, () -> userService.회원프로필(pageUserId, principalId));

			// then
			verify(subscribeRepository, never()).mSubscribeState(anyInt(), anyInt());
			verify(subscribeRepository, never()).mSubscribeCount(anyInt());
			// naver() 메서드가 호출이 되지 않았는지 여부 검즘
			// anyInt() 매개변수 값이 어떤 값이든 상관없다.

		}
	}
}
