<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<h2>Read</h2>
	<table>
		<tr>
			<td>userId</td>
			<td>${member.userId }</td>
		</tr>
		<tr>
			<td>userName</td>
			<td>${member.userName }</td>
		</tr>
		<tr>
			<td>auth - 1</td>
			<td>${member.authList[0].auth }</td>
		</tr>
		<tr>
			<td>auth - 2</td>
			<td>${member.authList[1].auth }</td>
		</tr>
		<tr>
			<td>auth - 3</td>
			<td>${member.authList[2].auth }</td>
		</tr>
	</table>
	<a href="/crud/member/modify?userNo=${member.userNo }">Modify</a>
	<button type="button" id="btnRemove">Remove</button>
	<button type="button" id="btnList">List</button>
</body>
<script type="text/javascript">
$(function() {
	var btnModify = $('#btnModify');
	var btnDelete = $('#btnDelete');
	var btnList = $('#btnList');
	
	var delForm = $('#delForm');
	
	//수정 버튼 클릭시 이벤트
	btnModify.on('click',function(){
		delForm.attr("action","/crud/board/modify");
		delForm.attr("method","get");
		delForm.submit();
	});
	
	// 삭제 버튼
	btnDelete.on('click',function(){
		if(confirm("정말로 삭제 하시겠습니까?")){
			delForm.submit();
		}
	});
	
	// 목록 버튼
	btnList.on('click',function(){
		location.href = "/crud/board/list";
	});
});
</script>
</html>