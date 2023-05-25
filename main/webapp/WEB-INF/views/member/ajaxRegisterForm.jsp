<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax Register Form</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<h1>9. Ajax 방식 요청 처리</h1>
	
	<h3>Ajax 방식 요청 처리</h3>
	<hr>
	<form action="">
		<p>userId : <input type="text" name="userId" value="worbs" id="userId"></p>
		<p>password : <input type="text" name="password" value="1234" id="password"></p>
	</form>
	
	<p>1) URL 경로 상의 경로 변수값을 @Pathvariable 어노테이션을 지정하여 문자열 매개변수로 처리</p>
	<button id="registerBtn01">registerBtn01</button>
	
	
	<p>2) URL 경로 상의 여러개의 경로 변수값을 @Pathvariable 어노테이션을 지정하여 문자열 매개변수로 처리</p>
	<button id="registerBtn02">registerBtn02</button>
	
	<p>3) 객체 타입의 JSON 요청 데이터 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리</p>
	<button id="registerBtn03">registerBtn03</button>
	
	<p>4) 객체 타입의 JSON 요청 데이터는 문자열 매개변수로 처리할수 없다.</p>
	<button id="registerBtn04">registerBtn04</button>
	
	<p>5) 요청 URL에 쿼리 파라미터를 붙여서 전달 하면 문자열 매개변수로 처리</p>
	<button id="registerBtn05">registerBtn05</button>
	
	<p>6) 객체타입의 JSON 요청 데이터를 @PathVariable 어노테이션을 지정한 문자열 매개변수와 @RequestBody 어노테이션을 지정한 자바빈즈 매개변수로 처리</p>
	<button id="registerBtn06">registerBtn06</button>
	
	<p>7) 객체배열 타입의 JSON 요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수 @RequestBody 어노테이션을 지정하여 처리</p>
	<button id="registerBtn07">registerBtn07</button>
	
	<p>8) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리</p>
	<button id="registerBtn08">registerBtn08</button>
	
	<p>9) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리</p>
	<button id="registerBtn09">registerBtn09</button>
</body>
<script type="text/javascript">
$(function(){
	
	$('#registerBtn01').on('click',function(){
		$.get("/ajax/register/hongkd", function(res) {
			console.log("res",res);
			if(res === "SUCCESS"){
				alert(res);
			}
		})
	});
	
	
	$('#registerBtn02').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password
		};
		$.ajax({
			type : 'post',
			url : "/ajax/register/"+userId +"/"+password,
			data : JSON.stringify(userObject),
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	$('#registerBtn03').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register03",
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	//4) 객체 타입의 JSON 요청 데이터는 문자열 매개변수로 처리할수 없다.
	$('#registerBtn04').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register04",
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	//5) 요청 URL에 쿼리 파라미터를 붙여서 전달 하면 문자열 매개변수로 처리
	$('#registerBtn05').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register05?userId="+userId,
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	// 6) 객체타입의 JSON 요청 데이터를 @PathVariable 어노테이션을 
	//    지정한 문자열 매개변수와 @RequestBody 어노테이션을 지정한 자바빈즈 매개변수로 처리
	$('#registerBtn06').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register06/"+userId,
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	$('#registerBtn07').on('click',function(){
		var userObjectArray = [
			{userId : "name01", password : "pw1"},	
			{userId : "name02", password : "pw2"}	
		];
		
		$.ajax({
			type : 'post',
			url : "/ajax/register07",
			data : JSON.stringify(userObjectArray), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	$('#registerBtn08').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password,
			address : {
				postCode : "010908",
				location : "Incheon"
			}
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register08",
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	$('#registerBtn09').on('click',function(){
		var userId = $('#userId').val();
		var password = $('#password').val();
		var userObject = {
			userId : userId,
			password : password,
			dateOfBirth : "20220202",
			cardList : [
				{no : "12345", validMonth : "20221014"},
				{no : "56768", validMonth : "20221015"},
				{no : "33333", validMonth : "20221016"}
			],
			address : {
				postCode : "010908",
				location : "Incheon"
			}
		};
		
		$.ajax({
			type : 'post',
			url : "/ajax/register09",
			data : JSON.stringify(userObject), // 제이슨 타입의 문자열
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res", res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
});
</script>
</html>















