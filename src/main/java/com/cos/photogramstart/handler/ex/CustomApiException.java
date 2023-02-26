package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException{

	// 객체 구분하기위한 시리얼넘버
	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message); 
	}
}
