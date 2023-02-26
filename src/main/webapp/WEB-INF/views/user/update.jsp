<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필셋팅 메인-->
<main class="main">
	<!--프로필셋팅 섹션-->
	<section class="setting-container">
		<!--프로필셋팅 아티클-->
		<article class="setting__content">

			<!--프로필셋팅 아이디영역-->
			<div class="content-item__01">
				<div class="item__img">
					<img src="#" onerror="this.src='/images/person.jpg'" />
				</div>
				<div class="item__username">
					<h2></h2>
				</div>
			</div>
			<!--프로필셋팅 아이디영역end-->

			<!--프로필 수정-->
			<form id="profileUpdate" onsubmit ="update(${ principal.user.id },event)">
			<!-- event 를 같이 넘겨서 페이지가 무작정 넘어가는것을 방지한다.  -->
			<!-- html 에서 막아줘도 postman이나 f12 관리자 도구로 required 없애는 경우 값이 변경 됨. 
					backend 단에서 막아줌 Validation 체크 -->
				<div class="content-item__02">
					<div class="item__title">이름</div>
					<div class="item__input">
						<input type="text" name="name" placeholder="이름"
							value="${ principal.user.name }"  required ="required"/>
					</div>
				</div>
				<div class="content-item__03">
					<div class="item__title">유저네임</div>
					<div class="item__input">
						<input type="text" name="username" placeholder="유저네임"
							value="${ principal.user.username }" readonly="readonly" />
					</div>
				</div>
				<div class="content-item__04">
					<div class="item__title">패스워드</div>
					<div class="item__input">
						<input type="password" name="password" placeholder="패스워드" required="required"/>
					</div>
				</div>
				<div class="content-item__05">
					<div class="item__title">웹사이트</div>
					<div class="item__input">
						<input type="text" name="website" placeholder="웹 사이트"
							value="${ principal.user.website }" />
					</div>
					
					<!-- 맥락이랑 상관은 없는데, 속도 향상을 위한 webclient? 
					https://velog.io/@chlwogur2/WebClient-%EA%B3%B5%EC%8B%9D-%EB%AC%B8%EC%84%9C-%EC%A0%95%EB%A6%AC 
					캐시 사용하기? - 방법을 좀 배워야할듯 -->
				</div>
				<div class="content-item__06">
					<div class="item__title">소개</div>
					<div class="item__input">
						<textarea name="bio" id="" rows="3">${ principal.user.bio }</textarea>
					</div>
				</div>
				<div class="content-item__07">
					<div class="item__title"></div>
					<div class="item__input">
						<span><b>개인정보</b></span> <span>비즈니스나 반려동물 등에 사용된 계정인 경우에도
							회원님의 개인 정보를 입력하세요. 공개 프로필에는 포함되지 않습니다.</span>
					</div>
				</div>
				<div class="content-item__08">
					<div class="item__title">이메일</div>
					<div class="item__input">
						<input type="text" name="email" placeholder="이메일"
							value="${ principal.user.email }" readonly="readonly" />
					</div>
				</div>
				<div class="content-item__09">
					<div class="item__title">전화번호</div>
					<div class="item__input">
						<input type="text" name="phone" placeholder="전화번호"
							value="${ principal.user.phone }" />
					</div>
				</div>
				<div class="content-item__10">
					<div class="item__title">성별</div>
					<div class="item__input">
						<input type="text" name="gender" value="${ principal.user.gender }" />
					</div>
				</div>
				<!--  각각  이렇게 집어넣는거 말고 시큐리티 라이브러리를 사용해서 쉽게 처리할 수 있음.  
						header 파일은 모든파일에서 불러오기 때문에 해더파일에 시큐리티 태크 사용해서 넣을 수 있음. 
						그리고 주석에다가 $~ principal 이엘 쓰면 얘도 인식해서 500에러가 발생함.. 뭐야?
						템플릿엔진스면 자바스크립트로 처리하는데 jsp 사용하면 이런거 알아둬야함. 아직 jsp 쓰는 회사 많음. 
				-->
				<!--제출버튼-->
				<div class="content-item__11">
					<div class="item__title"></div>
					<div class="item__input">
						<button>제출</button>
					</div>
				</div>
				<!--제출버튼end-->

			</form>
			<!--프로필수정 form end-->
		</article>
	</section>
</main>

<script src="/js/update.js"></script>

<%@ include file="../layout/footer.jsp"%>