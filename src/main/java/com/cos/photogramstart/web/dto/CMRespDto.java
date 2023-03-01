package com.cos.photogramstart.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// 둘 다 리턴할 수 있게 만들기위한 dto 
@ApiModel(description = "에러 상태")	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
	
	@ApiModelProperty(example = "코드1 성공, 코드-1 실패")
	private int code;
	
	@ApiModelProperty(example = "결과 메세지")
	private String message;

	@ApiModelProperty(example = "전송 데이터 객체")
	private T data;
}
