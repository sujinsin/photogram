# 프로젝트 이름

### Photogram


# 프로젝트 개요

#### 프로젝트의 목적은 
- SNS instagram을 클론 코딩 하여 기술을 익히기 위함
#### 주요 기능으로는 
- API 로그인, Spring Security 로그인(security 설정 등)
- 구독기능으로 구독한 유저들의 게시물을 구독자의 story 에서 무한스크롤링으로 게시물 출력
- story에 올라온 게시물 좋아요, 좋아요 취소
- 구독한 유저의 게시물에 댓글작성 
- 회원의 유저 정보 수정
- 좋아요 순으로 게시물을 보여주는 인기게시판 구현 

# 프로젝트 환경

- jdk-11
- sts-4.6.0.RELEASE
- Spring Boot
- JPA
- Maven
- MariaDB
- AWS EC2

# 사용한 라이브러리 

- Lombok
- qlrm
- Spring Security
- OAuth2
- JPA
- Starter-web
- DevTools
- MariaDB

# 설치방법

#### 각 라이브러리는 https://mvnrepository.com Maven 저장소에서 각 라이브러리를 검색해 pom.xml 파일에 의존성을 추가해주면 된다.

```xml



		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>


```

# 배포

	이 프로젝트는 AWS EC2와 Github Actions 자동배포를 사용하여 배포하였습니다. 

# 기능 구현

#### 로그인 기능
	Spring Security와 OAuth2를 사용하여 google, facebook, naver, kakao api 로그인 기능을 구현하였고, 
	일반 로그인 기능도 구현하였습니다. 
#### 구독 기능 
	다른 유저의 프로필페이지에서 구독하기 버튼을 클릭해 구독하고, 구독을 취소 할 수 있습니다. 
#### 구독한 유저들의 정보 확인
	프로필 페이지에서 해당 유저를 구독한 구독자들을 확인할 수 있고, 구독과 취소도 할 수 있습니다. 
#### 유저정보 수정 
	로그인한 사용자의 정보를 수정할 수 있습니다.
#### 인기페이지 
	인기페이지는 좋아요가 많은 게시물을 최신순으로 보여줍니다

