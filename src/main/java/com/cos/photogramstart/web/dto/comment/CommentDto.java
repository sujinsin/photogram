package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {
	
	@NotBlank // 빈값이거나 null 체크 빈공백(스페이스)
	private String content;
	
	// @NotBlank - int 값은 null 체크 못하니까 에러남
	// @NotEmpty - int는 이 어노테이션도 적용불가 
	// Integer로 값을 변경
	@NotNull // null값 체크 
	private Integer imageId;
	
}
