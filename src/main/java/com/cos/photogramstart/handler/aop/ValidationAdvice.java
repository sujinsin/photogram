package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;


@Component 
@Aspect
public class ValidationAdvice {// advice 공통기능을 의미함

	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") 
	public Object apiAdvice(	ProceedingJoinPoint proceedingJoinPoint) throws Throwable { 

		Object[] agrs = proceedingJoinPoint.getArgs();
		for(Object arg : agrs) {
			
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult)arg;
				
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
					
				}
			}
		}

		System.out.println("web api 컨트롤러============================");
		
		return proceedingJoinPoint.proceed(); 
	}
	
	
	//https://jonnastudy.tistory.com/2 해킹 관련 
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))") 
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		Object[] agrs = proceedingJoinPoint.getArgs();
		for(Object arg : agrs) {
			
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult)arg;
				
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					throw new CustomValidationException("유효성 검사 실패함", errorMap);
					
				}
			}
		}
		
		System.out.println("web 컨트롤러============================");
		return proceedingJoinPoint.proceed(); // 그 함수로 다시 돌아가라, 처리가 끝난 후 
	}
	
}
