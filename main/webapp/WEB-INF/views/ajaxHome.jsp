<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX HOME</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<body>
	<h3>AJAX HOME</h3>
	
	<form action="" method="get">
		boardNo : <input type="text" name="boardNo" id="boardNo"> <br>
		title : <input type="text" name="title" id="title"> <br>
		content : <textarea rows="3" cols="5" name="content" id="content"></textarea> <br>
		
		writer : <input type="text" name="writer" id="writer"> <br>
		<input type="button" id="btn" value="전송">
	</form>
	
	<div>
		<button id="putBtn">MODIFY(PUT)</button>
		<button id="putHeaderBtn">MODIFY(PUT WITH HEADER)</button>
		
		<h3>Content Type 매핑</h3>
		<button id="postBtn">MODIFY(POST)</button>
		<button id="putJsonBtn">MODIFY(PUT JSON)</button>
		<button id="putXMLBtn">MODIFY(PUT XML)</button>
		
		<h3>Accept 매핑</h3>
		<button id="getBtn">READ</button>
		<button id="getJsonBtn">READ(JSON)</button>
		<button id="getXMLBtn">READ(XML)</button>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var putBtn = $('#putBtn');
	var putHeaderBtn = $('#putHeaderBtn');
	
	var postBtn = $('#postBtn');
	var putJsonBtn = $('#putJsonBtn');
	var putXMLBtn = $('#putXMLBtn');
	
	var getBtn = $('#getBtn');
	var getJsonBtn = $('#getJsonBtn');
	var getXMLBtn = $('#getXMLBtn');
	
	putBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		var title = $('#title').val();
		var content = $('#content').val();
		var writer = $('#writer').val();
		
		var boardObject = {
			boardNo : boardNo,
			title : title,
			content : content,
			writer : writer
		};
		
		$.ajax({
			type : "put",
			url : "/board/"+boardNo,
			data : JSON.stringify(boardObject),	// 제이슨 문자열로 바꿔주는거다
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res : "+ res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	
	putHeaderBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		var title = $('#title').val();
		var content = $('#content').val();
		var writer = $('#writer').val();
		
		var boardObject = {
			boardNo : boardNo,
			title : title,
			content : content,
			writer : writer
		};
		/*
			** 헤더를 설정하는 이유 **
			 - 헤더 정보가 없으면 절대 못들어오게 한다.
			 api 만들때 쓴다.
		*/
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			headers : {
				"X-HTTP-Method-Override" : "PUT",
			},
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res",res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	postBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		var title = $('#title').val();
		var content = $('#content').val();
		var writer = $('#writer').val();
		
		var boardObject = {
			boardNo : boardNo,
			title : title,
			content : content,	
			writer : writer
		};
		
		$.ajax({
			type : "post",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res",res);
				// == 는 Equal Operator, === 는 Strict Equal Operator 값을더 엄격하게 비교할떄 사용한다.
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	putJsonBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		var title = $('#title').val();
		var content = $('#content').val();
		var writer = $('#writer').val();
		
		var boardObject = {
			boardNo : boardNo,
			title : title,
			content : content,	
			writer : writer
		};
		
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(res) {
				console.log("res",res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	putXMLBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		var title = $('#title').val();
		var content = $('#content').val();
		var writer = $('#writer').val();
		
		var xmlData = "";
		xmlData += "<Board>";
		xmlData += "<boardNo>" + boardNo + "</boardNo>";
		xmlData += "<title>" + title + "</title>";
		xmlData += "<content>" + content + "</content>";
		xmlData += "<writer>" + writer + "</writer>";
		xmlData += "</Board>";
		
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			data : xmlData,
			contentType : "application/xml; charset=utf-8", // 응답으로 받을 타입 설정
			success : function(res) {
				console.log("res",res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	getBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		
		// GET 방식 비동시 HTTP 요청수행
		$.get("/board/" + boardNo, function(data) {
			console.log(data);
			alert(JSON.stringify(data));
		});
	});
	
	
	getJsonBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		
		$.ajax({
			type : "get",
			url : "/board/" + boardNo,
			headers : {
				"Accept" : "application/json"
			},
			success : function(res) {
				console.log("res",res);
				alert(JSON.stringify(res));
			}
		});
	});
	
	
	getXMLBtn.on('click',function(){
		var boardNo = $('#boardNo').val();
		
		$.ajax({
			type : "get",
			url : "/board/" + boardNo,
			headers : {
				"Accept" : "application/xml"
			},
			success : function(res) {
				console.log("res",res);
				alert(xmlToString(res));
			}
		});
	});
	
});

function xmlToString(xmlData) {
	var xmlString;
	
	// window.ActiveXObject 는 ActiveObject를 지원하는 브라우저면
	// Object를 리턴하고 그렇지 않다면 null을 리턴한다.
	if(window.ActiveXObject){
		xmlString = xmlData.xml;
	}else{
		xmlString = (new XMLSerializer()).serializeToString(xmlData);
	}
	return xmlString;
}
</script>
</html>


















