<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberResult</title>
</head>
<body>
	<h3>맴버 결과</h3>
	<table border="1">
		<tr>
			<td><p>아이디 : ${member.userId }</p></td>
		</tr>
		<tr>
			<td><p>비밀번호 : ${member.password }</p></td>
		</tr>
		<tr>
			<td><p>이름 : ${member.userName }</p></td>
		</tr>
		<tr>
			<td><p>이메일 : ${member.email }</p></td>
		</tr>
		<tr>
			<td><p>생년월일 : <fmt:formatDate value="${member.dateOfBirth }" pattern="yyyy년 MM월 dd일"/></p></td>
		</tr>
		<tr>
			<td>
				<p>
					성별 : 
					<c:if test="${member.gender eq 'male'}">
						남자	
					</c:if> 
					<c:if test="${member.gender eq 'female'}">
						여자
					</c:if> 
					<c:if test="${member.gender eq 'other'}">
						중성
					</c:if>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					개발자여부 : <c:if test="${member.developer eq 'Y' }">개발자</c:if> 
					<c:if test="${member.developer ne 'Y' }">개발자 아님</c:if>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					외국인여부 : <c:if test="${member.foreigner}">외국인</c:if>
					<c:if test="${not member.foreigner}">한국인</c:if>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					국적 :	
					<c:forEach items="${member.nationality}" var="nation">
						<c:choose>
							<c:when test="${nation eq 'korea' }">
								한국
							</c:when>
							<c:when test="${nation eq 'germany' }">
								독일
							</c:when>
							<c:when test="${nation eq 'austrailia' }">
								호주
							</c:when>
							<c:when test="${nation eq 'canada' }">
								캐나다
							</c:when>
							<c:otherwise>
								미국
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					소유차량 : 
					<c:forEach items="${member.cars }" var="car">
						${car }    
					</c:forEach>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					취미 : 	
					<c:forEach items="${member.hobby }" var="hobby">
						<c:if test="${hobby eq 'sports' }">
							스포츠
						</c:if>
						<c:if test="${hobby eq 'music' }">
							음악
						</c:if>
						<c:if test="${hobby eq 'movie' }">
							영화
						</c:if>
					</c:forEach>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>우편번호 : ${member.address.postCode }</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>주소 : ${member.address.location }</p>
			</td>
		</tr>
		<tr>
			<td>
				<c:forEach items="${member.cardList }" var="card" varStatus="status">
					<c:if test="${status.index eq 0 }">
						<c:set value="카드1" var="name"/>
					</c:if>
					<c:if test="${status.index eq 1 }">
						<c:set value="카드2" var="name"/>
					</c:if>
					<p>
						${name } - 번호 : ${card.no } <br>
						${name } - 유효 : <fmt:formatDate value="${card.validMonth }" pattern="yyyy년 MM월 dd일"/>
					</p>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>
				<p>소개 : ${member.introduction }</p>
			</td>
		</tr>
	</table>
</body>
</html>