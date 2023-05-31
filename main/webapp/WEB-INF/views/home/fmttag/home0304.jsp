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
	<!-- 
		y : 년
		M : 월
		d : 일
		H : 시간
		m : 분
		s: 초
		z : 나라
		a : 오전/오후
		
	 -->
	<h3>pattern 속성을 지정하여 날짜를 포맷팅 한다.</h3>
	<p>now : ${now }</p>
	<p>pattern :  <fmt:formatDate value="${now }" pattern="yyyy-MM-dd HH:mm:ss"/>
	<p>pattern :  <fmt:formatDate value="${now }" pattern="a h:mm"/>
	<p>pattern :  <fmt:formatDate value="${now }" pattern="z a h:mm"/>
</body>
</html>



















