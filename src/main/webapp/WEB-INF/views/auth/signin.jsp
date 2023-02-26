<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Photogram</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
</head>

<body>
	<div class="container">
		<main class="loginMain">
			<!--로그인섹션-->
			<section class="login">
				<!--로그인박스-->
				<article class="login__form__container">
					<!--로그인 폼-->
					<div class="login__form">
						<h1>
							<img src="/images/logo.jpg" alt="">
						</h1>

						<!--로그인 인풋
                                있는지 SELECT 해서 처리하는데 로그인은 중요한 정보라 노출되면 안되기 때문에 주소창에 노출되면 안됨.
                                그래서 HTTP BODY에 데이터를 담아서 보내야함으로 POST 방식을 사용했다. 
                                SecurityConfig 클래스로 가서 -->
						<form class="login__input" action="/auth/signin" method="POST">
							<input type="text" name="username" placeholder="유저네임"
								required="required" /> <input type="password" name="password"
								placeholder="비밀번호" required="required" />
							<button>로그인</button>
						</form>
						<!--로그인 인풋end-->



						<!-- 또는 -->
						<div class="login__horizon">
							<div class="br"></div>
							<div class="or">또는</div>
							<div class="br"></div>
						</div>
						<!-- 또는end -->

						<!-- Oauth 소셜로그인 -->
						<div class="login__facebook">
							<button
								onclick="javascript:location.href='/oauth2/authorization/google'">
								<!-- 위의 oauth 주소는 sprinboot oauth-client  검색하면 튜토리얼 나온다. 고정 주소임  
                            		invalid client registration with id : google 
                            		yml에 레지스트 등록이 페이스북만 되어있는데 google을 사용해서 나는 에러 -->
								<i class="fab fa-google"></i> <span> google로 로그인 </span>
							</button>
						</div>
						<div class="login__facebook">
							<button
								onclick="javascript:location.href='/oauth2/authorization/facebook'">
								<i class="fab fa-facebook-f"></i> <span>Facebook으로 로그인</span>
							</button>
						</div>
						<div class="login__facebook">
							<button
								onclick="javascript:location.href='/oauth2/authorization/kakao'">
								  <i class="fas fa-comments"></i><span>&nbsp;Kakaotalk으로
									로그인</span>
							</button>
						</div>
						<div class="login__facebook">
							<button
								onclick="javascript:location.href='/oauth2/authorization/naver'">
								<i class="fal fa-rainbow"></i> <span>&nbsp;Naver 로
									로그인</span>
							</button>
						</div>
						<!-- Oauth 소셜로그인end -->
					</div>

					<!--계정이 없으신가요?-->
					<div class="login__register">
						<span>계정이 없으신가요?</span> <a href="/auth/signup">가입하기</a>
					</div>
					<!--계정이 없으신가요?end-->
				</article>
			</section>
		</main>

	</div>
</body>

</html>