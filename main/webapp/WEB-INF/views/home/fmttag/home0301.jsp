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
	<h3>type 속성을 date로 지정하여 날짜 포맷팅을 한다.</h3>
	<p>now : ${now }</p>
	<p>date default :  <fmt:formatDate value="${now }" type="date" dateStyle="default"/>
	<p>date short :  <fmt:formatDate value="${now }" type="date" dateStyle="short"/>
	<p>date medium :  <fmt:formatDate value="${now }" type="date" dateStyle="medium"/>
	<p>date long :  <fmt:formatDate value="${now }" type="date" dateStyle="long"/>
	<p>date full :  <fmt:formatDate value="${now }" type="date" dateStyle="full"/>
</body>
</html>



















