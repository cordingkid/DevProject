<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Result</h3>
	<table border="1">
		<tr>
			<td>${member.userId}</td>
			<td>${member.userName}</td>
			<td>${member.password}</td>
			<td>${member.email}</td>
			<td>${member.gender}</td>
		</tr>
	</table>
</body>
</html>