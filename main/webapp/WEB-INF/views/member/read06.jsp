<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Read06</title>
</head>
<body>
	<h1>Read06 Result</h1>
	
	user.userId : ${user.userId }<br>
	user.password : ${user.password }<br>
	user.userName : ${user.userName}<br>
	user.email : ${user.email}<br>
	user.birthDay : ${user.birthDay}<br>
	user.hobby : ${user.hobby}<br>
	user.gender : ${user.gender}<br>
	
	<h4>HobbyArray</h4>
	<c:forEach items="${user.hobbyArray }" var="hobby" varStatus="status">
		hobbyArray[${status.index }] : ${hobby }<br>
	</c:forEach>
	
	<h4>HobbyList</h4>
	<c:forEach items="${user.hobbyList }" var="hobby" varStatus="status">
		hobbyList[${status.index }] : ${hobby }<br>
	</c:forEach>
	user.foreginer : ${user.foreginer }<br> 
	user.developer : ${user.developer }<br> 
	user.nationality : ${user.nationality }<br> 
	<h4>user.address</h4>
	user.address.postCode : ${user.address.postCode }<br>
	user.address.location : ${user.address.location }<br>
	
	<h4>CarArray</h4>
	<c:forEach items="${user.carArray }" var="car" varStatus="status">
		carArray[${status.index }] : ${car }<br>
	</c:forEach>
	
	<h4>CarList</h4>
	<c:forEach items="${user.carList }" var="car" varStatus="status">
		carList[${status.index }] : ${car }<br>
	</c:forEach>
	
	
	
	<h4>user.cardList</h4>
	<c:forEach items="${user.cardList }" var="card">
		<c:out value="${card.no } ${card.validMonth}"/>
	</c:forEach><br>
	user.introduction : ${user.introduction } <br>
	user.dateOfBirth : ${user.dateOfBirth }
</body>
</html>