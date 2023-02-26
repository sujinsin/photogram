<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<%@ include file="../layout/header.jsp" %>

    <!--사진 업로드페이지 중앙배치-->
        <main class="uploadContainer">
           <!--사진업로드 박스-->
            <section class="upload">
               
               <!--사진업로드 로고-->
                <div class="upload-top">
                    <a href="home.html" class="">
                        <img src="/images/logo.jpg" alt="">
                    </a>
                    <p>사진 업로드</p>
                </div>
                <!--사진업로드 로고 end-->
                
                <!--사진업로드 Form-->
                <form class="upload-form" action="/image" method="post" enctype="multipart/form-data" >
                    <input  type="file" name="file"  onchange="imageChoose(this)"/><!-- 실제 파일 = 바이트 데이터로 날라간다? -->
                    <div class="upload-img">
                        <img src="/images/person.jpg" alt="" id="imageUploadPreview" />
                    </div>
                    
                    <!--사진설명 + 업로드버튼-->
                    <div class="upload-form-detail">
                   		 <input type="text" placeholder="사진설명" name="caption"/>
                   		 <!-- key=value , byte 2개의 타입을전송해야하므로 appl~ 는 안날라감.
                   		  		 그래서 여러종류의 타입을 묶어서 날리는 Multipart/form-data 타입으로 날림  
                   		  		 파일, 키벨류 두가지 타입보내고싶을때 사용하는 타입. -->
                        <button class="cta blue">업로드</button>
                    </div>
                    <!--사진설명end-->
                    
                </form>
                <!--사진업로드 Form-->
            </section>
            <!--사진업로드 박스 end-->
        </main>
        <br/><br/>
	
	<script src="/js/upload.js" ></script>
    <%@ include file="../layout/footer.jsp" %>