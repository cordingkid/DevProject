<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	${user.userName }님 환영합니다.
	
	<h3>증명서</h3>
	<table border="1">
		<tr>
			<th>종류</th>
			<th>출력</th>
		</tr>
		<tr>
			<td>재학 증명서</td>
			<td>
				<input type="button" value="출력" id="pdf1Btn">
			</td>
		</tr>
		<tr>
			<td>졸업 증명서</td>
			<td>
				<input type="button" value="출력" id="pdf2Btn">
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
$(function(){
	var pdf1Btn = $('#pdf1Btn');
	var pdf2Btn = $('#pdf2Btn');
	
	
	pdf1Btn.on('click',function(){
		$.ajax({
			type : "post",
			url : "/itext/download01",
			success : function(res) {
				alert(res);
			}
		});
	});
	
	pdf2Btn.on('click',function(){
		
	});
	
	
});

</script>
</html>