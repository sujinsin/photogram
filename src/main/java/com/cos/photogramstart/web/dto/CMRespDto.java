package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 둘 다 리턴할 수 있게 만들기위한 dto 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
	
	private int code;
	private String message;
	private T data;
}
