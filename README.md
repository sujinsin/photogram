<br><br>

# 프로젝트 이름
### Photogram

<br>

# 프로젝트 개요

### 프로젝트의 목적
	instagram의 클론코딩으로 다양한 라이브러리를 사용해보고 공부하기 위해
### 주요 기능은
- OAuth2를 이용해 api 로그인을 구현 

- spring security로 보안 강화

- rest api를 통해 api 를 구현

- 구독기능으로 구독한 유저들의 게시물들을 스크롤링으로 구현

- 좋아요, 좋아요 취소 

- 회원 정보 수정

- 좋아요 순의 인기페이지 게시판

- 게시물 댓글 작성, 삭제

	<br>
	
# 프로젝트 환경

- jdk-11

- sts-4.6.0.RELEASE

- Spring Boot

- JPA

- Maven

- MariaDB

- AWS EC2

- jsp

<br>

# 사용한 라이브러리

- Lombok

- QLRM

- Spring Security

- OAuth2

- Starter-Web

- DevTools

- MariaDB

<br>

# 설치방법

- 각 라이브러리는 https://mvnrepository.com Maven 저장소에서 검색해 원하는 의존성을 추가할 수 있다.

``` xml

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		
		.
		.
		.
		.
		.

```
<br>

# YML 설정

```yml

server:
  port: 8082
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true
    
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
      
  datasource:
    driver-class-name: ${ MARIADB_DRIVER_CLASS_NAME }
    url: ${ MARIADB_URL }
    username: ${ MARIADB_USERNAME }
    password: ${ MARIADB_PASSWORD }

  jpa:
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      ddl-auto: update
      use-new-id-generator-mappings: false
    open-in-view: true
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        
  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: ${ SECURITY_NAME }
      password: ${ SECURITY_PASSWORD }

    oauth2:
      client:
        registration:
          google:
            client-id: ${ GOOGLE_CLIENT_ID }
            client-secret: ${ GOOGLE_CLIENT_SECRET }
            scope:
              - email
              - profile
          facebook:
            client-id: ${ FACEBOOK_CLIENT_ID }
            client-secret: ${ FACEBOOK_CLIENT_SECRET }
            scope: 
            - public_profile
            - email        
          kakao:
            client-id: ${ KAKAO_CLIENT_ID }
            redirect-uri: ${ KAKAO_REDIRECT_URI }
            client-authentication-method: POST
            authorization-grant-type: authorization_code
            scope: 
            - profile_nickname
            - profile_image
            - account_email
            - gender
            client-name: Kakao
          naver:
            client-id: ${ NAVER_CLIENT_ID }
            client-secret: ${ NAVER_CLIENT_SECRET }
            redirect-uri: ${ NAVER_REDIRECT_URI }
            client-authentication-method: POST
            authorization-grant-type: authorization_code      
            scope: name,email
            client-name: Naver
        provider:
          kakao:
            authorization-uri: ${ KAKAO_AUTHORIZATION_URI }
            token-uri: ${ KAKAO_TOKEN_URI }
            user-info-uri: ${ KAKAO_USER_INFO_URI }
            user-name-attribute: id      
          naver:
            authorization-uri: ${ NAVER_AUTHORIZATION_URI }
            token-uri: ${ NAVER_TOKEN_URI }
            user-info-uri: ${ NAVER_USER_INFO_URI }
            user-name-attribute: response

file:
  path: ${ FILE_PATH }


```

<br>

# 배포

	이 프로젝트는 AWS EC2와 Github Actions 자동배포를 사용하여 배포하였습니다.
	
<br>

# 기능 설명

<br>

- 로그인 기능

	- Spring Security와 OAuth2를 사용하여 로그인 기능을 구현하였습니다. 
	
	- 사용자는 OAuth2 Provider를 통해 최초로그인시 자동으로 디비에 데이터를 저장하고, 
	
	- 최초 로그인이 아닐경우 바로 로그인이 실행됩니다.

- 구독기능

	- 구독한 유저의 게시물을 story 피드에서 확인할 수 있도록 구독기능을 구현하였습니다. 
	
- 좋아요 기능

	- 유저들의 게시물 이미지에 좋아요를 누를 수 있고, 
	
	- 좋아요 취소를 할 수 있습니다.
	
- 댓글 

	- story 페이지에서 게시물에 댓글을 작성할 수 있습니다.
	
- 유저 정보 수정 

	- 로그인 유저의 정보를 수정할 수 있습니다
	
- 인기페이지 

	- 좋아요를 많이 받은 게시물 순으로 이미지를 보여주는 게시판을 구현하였습니다.
	
<br><br>
