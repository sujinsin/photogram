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

# 공통 YML 설정

```yml
#공통요소들
server:
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
      
  profiles:
    active: prod

  jpa:
    database-platform: org.hibernate.dialect.MariaDBDialect
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      use-new-id-generator-mappings: false
    open-in-view: true

  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

springfox:
  documentation:
    swagger:
      use-model-v3: false


```



<br>
# 배포용 YML 설정

```prod.yml

server:
  port: 8080

spring:

  datasource:
    url: ${/config/photogram/mariadb.url}
    username: ${/config/photogram/mariadb.username}
    password: ${/config/photogram/mariadb.password}
    driver-class-name: org.mariadb.jdbc.Driver

  security:

    oauth2:
      client:
        registration:
          google:
            client-id: ${/config/photogram/google.cliend.id}
            client-secret: ${/config/photogram/google.cliend.secret}
            scope:
            - email
            - profile
          kakao:
            client-id: ${/config/photogram/kakao.cliend.id}
            redirect-uri: ${/config/photogram/kakao.redirect.uri}
            client-authentication-method: POST
            authorization-grant-type: authorization_code
            scope:
            - profile_nickname
            - profile_image
            - account_email
            - gender
            client-name: Kakao
          naver:
            client-id: ${/config/photogram/naver.cliend.id}
            client-secret: ${/config/photogram/naver.cliend.secret}
            redirect-uri: ${/config/photogram/naver.redirect.uri}
            client-authentication-method: POST
            authorization-grant-type: authorization_code
            scope: name,email
            client-name: Naver
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
            
            
awsParameterStorePropertySource:
  enabled: true
                   
cloud:
  aws:
    credentials:
      accessKey: ${/config/photogram/cloud.aws.credentials.accessKey}
      secretKey: ${/config/photogram/cloud.aws.credentials.secretKey}
    s3:
      bucket: ${/config/photogram/cloud.aws.credentials.bucket}
    region:
      static: ${/config/photogram/cloud.aws.credentials.region}



```

<br>

# 배포


	이 프로젝트는 AWS EC2 서버를 사용해 배포하였고, 
	디비 관리는 RDS, 
	이미지 관리는 S3 를 사용합니다. 
	application은 개발용, 배포용을 따로 설정해 관리하며 git에는 올리지 않았습니다. 
	prod 배포용 yml에는 accessKey, Open Api 등의 비밀번호 등이 설정 되어있으며 
	application-pro.yml 에도 중요한 비밀번호들은 ${} 플레이스 홀더로 등록해 비밀번호 노출을 최소화했고,
	parameter store의 환경변수를 자동으로 가져와 치환해 사용할 수 있도록 환경셋팅을 하였습니다. 
	카페24에서 DNS를 구매하여 URL을 직관적으로 구현하였으며, Cloudflare를 이용하여 SSL/TLS 인증서를 발급하고 
	HTTPS 통신을 구현하여 웹 사이트의 보안성을 높였습니다. 이를 통해 안전하고 안정적인 서버 URL을 사용할 수 있게 구현하였습니다. 
	
	
<br>

# 기능 설명

<br>

- 로그인 기능

	- Spring Security와 OAuth2를 사용하여 로그인 기능을 구현하였습니다. (google, kakao, naver)
	
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

- 자잘하게 구현해본 기능
	- 10mb 이하의 이미지만 업로드 가능 하며 2mb 이상 이미지의 경우 리사이징 하여 2mb 이하로 사이즈 조정 
	- 메인페이지(집모양아이콘) 의 이미지 더블클릭 시 좋아요 가능, 하트 in-out 모션 기능 
	- api 명세서 swagger 사용
	
	
<br><br>
