package com.cos.photogramstart.domain;

import java.util.Map;

import com.cos.photogramstart.domain.common.RoleType;
import com.cos.photogramstart.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
    private String name;
    private String email;
    private String gender;
    
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String gender) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
    
    public static OAuthAttributes of(String socialName, String userNameAttributeName, Map<String, Object> attributes){
        // 카카오         
        if("kakao".equals(socialName)){
            return ofKakao("id", attributes);
        }
        
        return null;
    }
    
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .gender((String)kakaoAccount.get("gender"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }
    
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .role(RoleType.USER)
                .build();
    }
}