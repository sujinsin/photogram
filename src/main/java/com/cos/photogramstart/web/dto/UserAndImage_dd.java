package com.cos.photogramstart.web.dto;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserAndImage_dd {
	
	private User user;
	
	private Image image;
	
}
