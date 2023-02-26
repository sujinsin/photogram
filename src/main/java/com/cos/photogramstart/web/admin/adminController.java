package com.cos.photogramstart.web.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class adminController {
	
	
	@GetMapping("/admin")
	public String testView() {
		
		return "뭔뎅? 돠ㅣ는거냐 ";
	}
}
