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
			<td>국적</td>
			<td>
				${nationality }
			</td>
		</tr>
		<tr>
			<td>내 자동차 현황</td>
			<td>
				<c:forEach items="${member.carList }" var="car">
					<c:if test="${car ne '' }">
						${car } <br>
					</c:if>
				</c:forEach>
			</td>
		</tr>
	</table>
</body>
</html>