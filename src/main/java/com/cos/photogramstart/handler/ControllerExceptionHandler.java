package com.cos.photogramstart.handler;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	// sql 오류 발생시 
	@ExceptionHandler(ConstraintViolationException.class)
	public String sqlException(ConstraintViolationException e) {
			return Script.back("이미 존재하는 아이디입니다");
	}

	@ExceptionHandler(CustomValidationException.class) // 런타임CustomValidationException 발생시 해당 메서드 구문 수행.
	public String validationException(CustomValidationException e) {
		
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString().replaceAll("[{}]","")); 			
		}
	}
	
	// 4mb 용량이 넘을때 발생하는 에러 
	@ExceptionHandler(value = {MaxUploadSizeExceededException.class})
	public String fileException(Exception e, HttpServletRequest request) {
		
			// ajax 에서 넘어온 요청일경우  
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				return Script.backAjax("파일 용량이 범위값을 초과합니다. 4MB 이하 이미지만 업로드 해주세요");
			}else { // form 데이터로 넘어오는 경우
				return Script.back("파일 용량이 범위값을 초과합니다. 4MB 이하 이미지만 업로드 해주세요");
			}
	}
	
	
	// 데이터를 리턴함.  ajax 할때는 데이터를 리턴해야함. 
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
	
	// 뭘 리턴할지 모르겠을때 ? 를 집어넣으면 추론을 통해 타입매치를 시켜줌 
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	// 해당 유저가 존재하지 않을때 
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> idNotFoundException(UsernameNotFoundException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
	}
		
}