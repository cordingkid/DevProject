<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	<form action="/login1" method="post">
		<input type="text" name="userId" placeholder="아이디"> <br>
		<input type="text" name="userPw" placeholder="비밀번호"><br>
		<input type="submit" value="로긴">
	</form>
</body>
</html>