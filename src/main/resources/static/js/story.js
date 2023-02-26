
// 현재 로긴한 사용자 아이디
let principalId = $("#principalId").val();
// haeder 에 hiddne 으로 input 에 담아놨던거 


// 스토리 로드하기
let page = 0;
function storyLoad() {
	$.ajax({
		url:`/api/image?page=${page}`,
		dataType:"json"
	}).done(res =>{
		console.log(res);
		res.data.content.forEach((image) =>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		})
	}).fail(error =>{
		console.log("오류",error)
	});
}

storyLoad();

function getStoryItem(image) {
	let imageurl = `${image.user.profileImageUrl}` + "";
//	alert(imageurl);
	let item = `<!--전체 리스트 아이템-->
<div class="story-list__item">
	<div class="sl__item__header">
		<div>
			<a href="/user/${image.user.id}"><img class="profile-image" src="${image.user.profileImageUrl}" 
				onerror="this.src='/images/person.jpg'" onclick="imageurl(this)"/></a>
		</div>
		<div><a href="/user/${image.user.id}">${ image.user.username }</a></div>
	</div>

	<div class="sl__item__img" ondblclick="toggleLike(${image.id})">
		<img src="${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;

				if(image.likeStatus) {
					item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
				}else {
					item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
				}
			item += `
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.id}">${ image.likeCount }</b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>
		</div>

		<div id="storyCommentList-${image.id}">`;

			image.comments.forEach((comment) => {
				item +=`	<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"">
				<p>
					<b>${ comment.user.username } :</b> ${comment.content}
				</p>`;

				if(principalId == comment.user.id) {
					item +=`<button onclick="deleteComment(${comment.id})">
						<i class="fas fa-times"></i>
					</button>`					
				}


			item += `
			</div>`;
			});

			item +=`
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;
	return item;
}

// 스토리 스크롤 페이징하기
$(window).scroll(() => {
//	console.log("윈도우 스크롤탑 = ",$(window).scrollTop());
//	console.log("도큐먼트 높이 = ",$(document).height());
//	console.log("윈도우 높이 = ",$(window).height());
	
	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	console.log(checkNum);
	
	if(checkNum < 1 && checkNum > -1) {
		page++;
		storyLoad();
	}
});


// 좋아요, 좋아요취소
function toggleLike(imageId) {
	
	let likeIcon = $("#storyLikeIcon-"+imageId);
	
	if (likeIcon.hasClass("far")) {// 좋아요 하겠다. 
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res =>{
			$(`#storyLikeCount-${imageId}`).text(res.data);
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
			
		}).fail(error =>{
			console.log("오류",error)
		});		


	} else {
		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res =>{
			$(`#storyLikeCount-${imageId}`).text(res.data);
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
			
		}).fail(error =>{
			console.log("오류",error)
		});	

	}
	
	$("#heart_id").css("display","inline-block").addClass("heart_size"); 
	setTimeout(function () { $("#heart_id").css("display","none").removeClass("heart_size")}, 1000);
}

// 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId, // 서버에서 validation걸어줄때 int라서 notEmpty로 빈값 체크만 하게 해야함 notblank 는 null값도 체크해 validation type 에러 발생
		content: commentInput.val()
	}

	// 댓글 유효성 검사를 위해 비어있도록 설정을 해주야함. 
	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type:"post",
		url:"/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType:"json"
	}).done(res =>{
	let comment = res.data; // 아이디 줘야지 삭제함. 
	
	let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b>${ comment.user.username } :</b>
		      ${ comment.content }
		    </p>
		    
		    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
		    
		  </div>
		`;
	commentList.prepend(content); // 최신댓글이 위로가야하니까 위로 넣음
	
	}).fail(error =>{
		console.log("오류 : ",error.responseJSON.data.content)
		alert("오류 : " + error.responseJSON.data.content);
	});	

	// 댓글 쓰고 input창 비워주기
	commentInput.val("");
}

// 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res =>{
		console.log("성공" , res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error =>{
		console.log("오류",error)
		alert("오류 : " + error.responseJSON.message);
	});	
}

function imageurl(imageurl) {
	console.log(imageurl.getAttribute('src'));
	
}






