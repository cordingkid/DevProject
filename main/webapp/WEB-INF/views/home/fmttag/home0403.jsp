<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>dateStyle 속성을 short로 지정하여 문자열을 Date객체로 변환한다.</h3>
	<p>dateValue : ${dateValue }</p>
	<fmt:parseDate value="${dateValue }" pattern="yyyy-MM-dd HH:mm:ss" var="date"/>
	<p>date : ${date }</p>
</body>
</html>



















