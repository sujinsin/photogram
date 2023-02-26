package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.OAuthAttributes;
import com.cos.photogramstart.domain.common.RoleType;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		// userRequest응답정보를 통해서 파싱해서 정보를 줌 // 스트링으로 정보가 날라오는데, 파싱해서 넣어준다.

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// 소셜 정보 가져옴 naver, kakao, google, facebook

		String social = oAuth2User.getName();

		User user;

		Map<String, Object> userInfo = oAuth2User.getAttributes();
		// 맵타입으로 들고있음 반환타입 맵타입으로 받아줌

		String name = null;
		String email = null;

		if (registrationId.equals("kakao")) {

			OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
					oAuth2User.getAttributes());
			user = attributes.toEntity();

		} else if (registrationId.equals("naver")) {
			Map<String, String> response = (Map<String, String>) userInfo.get("response");

			name = (String) response.get("name");
			email = (String) response.get("email");
			social = (String) response.get("id");
			user = User.builder().name(name).email(email).build();

		} else {
			name = (String) userInfo.get("name");
			email = (String) userInfo.get("email");
			user = User.builder().name(name).email(email).build();
		}

		String username = registrationId + "_" + social;

		String password = encoder.encode(UUID.randomUUID().toString());
		// 패스워드는 비크립트로 암호화가 되어 나와야함. 패스워드로 로그인할게 아니라 아무값이나 넣어도 됨

		User userEntity = userRepository.findByUsername(username);
		// 같은 유저가 계속해서 로그인하면 계속 의미없는 insert 가 일어나니까 막아야함

		if (userEntity == null) {
			user.setPassword(password);
			user.setUsername(username);
			user.setRole(RoleType.USER);

			// oauth로 로그인했는지 아닌지 구분하기위해서 넣어줌
			return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());

		} else {

			// 최초 로그인이 아니다/ 이미 회원가입이 되어있을때 타는 곳
			return new PrincipalDetails(userEntity);

		}
	}
}