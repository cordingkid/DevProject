<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Read04</title>
</head>
<body>
	<h1>Read04 Result</h1>
	
	
	<h4>HobbyArray</h4>
	<c:forEach items="${hobbyArray }" var="hobby" varStatus="status">
		hobbyArray[${status.index }] : ${hobby }<br>
	</c:forEach>
	
	<h4>HobbyList</h4>
	<c:forEach items="${hobbyList }" var="hobby" varStatus="status">
		hobbyList[${status.index }] : ${hobby }<br>
	</c:forEach>
	
	
	<h4>CarArray</h4>
	<c:forEach items="${carArray }" var="car" varStatus="status">
		carArray[${status.index }] : ${car }<br>
	</c:forEach>
	
	<h4>CarList</h4>
	<c:forEach items="${carList }" var="car" varStatus="status">
		carList[${status.index }] : ${car }<br>
	</c:forEach>
</body>
</html>