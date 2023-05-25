<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>registerRedirectAttributeFrom</h1>
	<hr>
	<form action="/redirectattribute/register" method="post">
		userId : <input type="text" name="userId" value="worbs"><br>
		password : <input type="password" name="password" value="1234"><br>
		<input type="submit" value="register"><br>
	</form>
</body>
</html>