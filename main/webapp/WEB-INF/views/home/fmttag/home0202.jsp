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
	<h3>3) type 속성이 currency인 경우</h3>
	<p>coin : ${coin }</p>
	<fmt:parseNumber value="${coin }" type="currency" var="coNum"/>
	<p>coNum : ${coNum }</p>
</body>
</html>



















