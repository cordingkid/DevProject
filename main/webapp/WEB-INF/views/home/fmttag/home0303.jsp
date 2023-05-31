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
	<h3>type 속성을 both로 지정하여 시간 포맷팅을 한다.</h3>
	<p>now : ${now }</p>
	<p>both default :  <fmt:formatDate value="${now }" type="both" timeStyle="default"/>
	<p>both short :  <fmt:formatDate value="${now }" type="both" timeStyle="short"/>
	<p>both medium :  <fmt:formatDate value="${now }" type="both" timeStyle="medium"/>
	<p>both long :  <fmt:formatDate value="${now }" type="both" timeStyle="long"/> 
	<p>both full :  <fmt:formatDate value="${now }" type="both" timeStyle="full"/>
</body>
</html>



















