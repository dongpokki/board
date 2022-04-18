<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/board.reply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/videoAdapter.js"></script>
<div class="page-main">
	<h2>${board.title}</h2>
	<ul>
		<li>번호 : ${board.board_num}</li>
		<li>작성자 : ${board.id}</li>
		<li>조회수 : ${board.hit}</li>
		<c:if test="${!empty board.modify_date}">
			<li>최근 수정일 : ${board.modify_date}</li>
		</c:if>
		<li>작성일 : ${board.reg_date}</li>
		<c:if test="${!empty board.filename}">
			<li>첨부 파일 : <a href="file.do?board_num=${board.board_num}">${board.filename}</a></li>
		</c:if>
	</ul>
	<hr size="1" width="100%" noshade="noshade">
	<c:if test="${fn:endsWith(board.filename,'.jpg') || 
				  fn:endsWith(board.filename,'.JPG') ||
				  fn:endsWith(board.filename,'.gif') || 
				  fn:endsWith(board.filename,'.GIF') ||
				  fn:endsWith(board.filename,'.png') ||
				  fn:endsWith(board.filename,'.PNG')}">
		<div class="align-center">
			<img src="imageView.do?board_num=${board.board_num}" style="max-width:500px">
		</div>
	</c:if>
	<p>
		${board.content}
	</p>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-right">
		<c:if test="${!empty user_num && user_num == board.mem_num}">
			<input type="button" value="수정" onclick="location.href='update.do?board_num=${board.board_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					let choice = confirm('삭제 하시겠습니까?');
					if(choice){
						location.replace('delete.do?board_num=${board.board_num}');
					}
				};
			</script>
		</c:if>
		<input type="button" value="목록" onclick="location.href='list.do'">
	</div>
	<hr size="1" width="100%" noshade="noshade">
	<div id="reply_div">
		<span class="re-title">댓글 달기</span>
		<form id="re_form">
			<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
			><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<c:if test="${!empty user_num}">
			<div id="re_first">
				<span class="letter-count">300/300</span>
			</div>
			<div id="re_second" class="align-right">
				<input type="submit" value="전송">
			</div>
			</c:if>
		</form>
	</div>
	<!-- 댓글 목록 출력 -->
	<div id="output"></div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="다음글 보기">
	</div>
	<div id="loading" style="display:none;">
		<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
	</div>
</div>
<!-- 중앙 컨텐츠 끝 -->