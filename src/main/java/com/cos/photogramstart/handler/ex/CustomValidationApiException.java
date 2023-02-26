package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errorMap;
	
	public CustomValidationApiException(String message,Map<String,String> errorMap) {
		super(message); // 부모에게 메세지 넘겨줘도 됨. 부모에게 던짐. 그럼 에러메시지로 출력할 수 있게 로직이 짜있음.
		this.errorMap = errorMap;
	}
	
	public CustomValidationApiException(String message) {
		super(message); 
	}

	public Map<String,String> getErrorMap() {
		return errorMap;
	} 
	
}
