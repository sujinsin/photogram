package com.cos.photogramstart.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

// ajax 로 요청하면 응답을 파일을 리턴하는게 아니라 데이터를 응답해줘야함.  
// 데이터를 응답해주는게 api  // api는 전부 RestController

@RequiredArgsConstructor
@RestController
@Api(tags = "User API")
public class UserApiController {

	private final UserService userService;

	private final SubscribeService subscribeService;

	@Operation(description = "유저의 대표 프로필 사진을 등록합니다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "프로필 등록 성공", response = CMRespDto.class),
		@ApiResponse(code = 405, message = "프로필 사진 file null", response = CMRespDto.class),
		@ApiResponse(code = 500, message = "프로필 사진 등록 실패", response = CMRespDto.class)
	})
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "principalId", value = "유저 번호", example = "2", required = true, paramType = "path"),
			@ApiImplicitParam(name = "profileImageFile", value = "유저의 프로필 사진  MultipartFile 객체", example = "MultipartFile 객체 전송",required = true, paramType = "body", dataTypeClass =  MultipartFile.class) 
	})
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (profileImageFile == null)
			return new ResponseEntity<>(new CMRespDto<>(-1, "프로필 이미지 파일이 전송되지 않았습니다.", null), HttpStatus.BAD_REQUEST);
		User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);
		principalDetails.setUser(userEntity);
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
	}

	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
	}

	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		if (id == principalDetails.getUser().getId()) {

			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);

			return new CMRespDto<>(1, "회원수정완료", userEntity);
		} else {
			throw new CustomApiException("비정상 접근 수정 불가");
		}
	}
}

//전처리
// 값 검증 
// 프론트단에서 유효성검사 처리 
// 백단 앞부분에서도 유효성검사 처리

// 디비에서 확인해서 처리 
// 후처리 
// 1번 유저 수정 
// -> 영속화
// -> 1번 유저 없음
