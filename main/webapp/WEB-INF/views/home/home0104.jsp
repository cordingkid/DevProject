<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>4. 표현언어(EL)을 이용하여 출력</h3>
	<p>1) 자바 빈즈 프로퍼티를 조회 하는경우 '속성명.프로퍼티명'을 지정한다.</p>
	<table border="1">
		<tr>
			<td>\${memberMap.["id"]}</td>
			<td>${memberMap.id}</td>
		</tr>
		<tr>
			<td>\${memberMap.["pwd"]}</td>
			<td>${memberMap.["pwd"]}</td>
		</tr>
		<tr>
			<td>\${memberMap.["email"]}</td>
			<td>${memberMap.email}</td>
		</tr>
		<tr>
			<td>\${memberMap.["name"]}</td>
			<td>${memberMap.name}</td>
		</tr>
	</table>
</body>
</html>



















