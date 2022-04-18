<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 중앙 컨텐츠 시작 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//비밀번호 변경 체크
		$('#passwd').keyup(function(){
			if($('#confirm_passwd').val() != '' && $('#confirm_passwd').val() != $(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#confirm_passwd').val() != '' && $('#confirm_passwd').val() == $(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','blue');
			}
		});
		
		$('#confirm_passwd').keyup(function(){
			if($('#passwd').val() != '' && $('#passwd').val() != $(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#passwd').val() != '' && $('#passwd').val() == $(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','blue');
			}
		});
		
		$('#change_form').submit(function(){
			if($('#now_passwd').val().trim()==''){
				alert('현재 비밀번호를 입력하세요');
				$('#now_passwd').val('').focus();
				return false;
			}
			if($('#passwd').val().trim()==''){
				alert('변경할 비밀번호를 입력하세요!');
				$('#passwd').val('').focus();
				return false;
			}
			if($('#confirm_passwd').val().trim()==''){
				alert('변경할 비밀번호 확인을 입력하세요!');
				$('#confirm_passwd').val('').focus();
				return false;
			}
			if($('#passwd').val()!=$('#confirm_passwd').val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
				return false;
			}
		});
		
	});
</script>
<div class="page-main">
	<h2>비밀번호 변경</h2>
	<form:form modelAttribute="memberVO" action="changePassword.do" id="change_form">
		<form:errors element="div" cssClass="error-color"/>
			<ul>
				<li>
					<form:label path="now_passwd">현재 비밀번호</form:label>
					<form:password path="now_passwd"/>
					<form:errors path="now_passwd" cssClass="error-color"/>
				</li>
				<li>
					<form:label path="passwd">변경할 비밀번호</form:label>
					<form:password path="passwd"/>
					<form:errors path="passwd" cssClass="error-color"/>
				</li>
				<li>
					<label for="confirm_passwd">변경할 비밀번호 확인</label>
					<input type="password" id="confirm_passwd">
					<span id="message_id"></span>
				</li>
			</ul>
			<div class="align-center">
				<form:button>전송</form:button>
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
	</form:form>
</div>
<!-- 중앙 컨텐츠 끝 --> 