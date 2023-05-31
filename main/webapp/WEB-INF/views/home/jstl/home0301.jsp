<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>7장 JSP</h3>
	<p>JSTL 태그들의 Example ::: c:set</p>
	<p>1) escapeXml 속성의 기본값은 true이고, 특수 문자를 반환한다.</p>
	<c:set value="${member.userId }" var="memberId"/>
	<table border="1">
		<tr>
			<td>member.userId</td>
			<td>${memberId}</td>
		</tr>
	</table>
	<hr>
	<!-- 값을 변경한다. -->
	<c:set target="${member }" property="userId" value="qwe123"/>
	
	<table border="1">
		<tr>
			<td>member.userId</td>
			<td><c:out value="${member.userId}" default="값이 없어"/></td>
		</tr>
	</table>
	
	<c:remove var="memberId"/>
	<p>memberId 변수를 삭제했음</p>
	<table border="1">
		<tr>
			<td>member.userId</td>
			<td><c:out value="${memberId}" default="값이 없어"/></td>
		</tr>
	</table>
</body>
</html>



















