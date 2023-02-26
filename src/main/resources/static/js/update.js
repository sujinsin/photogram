// (1) 회원정보 수정
function update(userId,event) {
	event.preventDefault();
	let data = $("#profileUpdate").serialize(); 
	
	$.ajax({
		type: "put",
		url : `/api/user/${userId}`, 
		data : data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType : "json"
	}).done(res => { 
		location.href=`/user/${userId}`;
	}).fail(error => {
			if(error.data == null) {
				alert(error.responseJSON.message);
			}else {
				alert(	JSON.stringify(error.responseJSON.data));				
			}
	});
	// 브라우저 끼리 통신할대는 http 상태코드를 같이 던져주는게 좋다. 
 };