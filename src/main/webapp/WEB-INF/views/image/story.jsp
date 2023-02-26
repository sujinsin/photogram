<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<section class="container">
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">
			<div id="toggle_heart"><i class="fas fa-heart" id="heart_id" style="display:none;"></i></div>
			<div id="user_image" class="" style="display:none;"><img class="user_image_size" src="/images/food1.jpg" width="100%" height="100%"></div>
		</article>
	</section>
</main>
<script src="/js/story.js"></script>
</body>
</html>
