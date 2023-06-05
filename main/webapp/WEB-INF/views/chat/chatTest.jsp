<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket 실시간 알림</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>WebSocket 실시간 알림</h1>
   
    <div id="chatBox" style="height: 300px; overflow-y: scroll; border: 1px solid #ccc; padding: 10px;"></div>
   
    <input type="text" id="messageInput" placeholder="메시지를 입력하세요" style="margin-top: 10px;">
    <button id="sendButton">전송</button>
   
    <script>
        var socket = new WebSocket("ws://localhost:80/websockettest");

        socket.onopen = function() {
            console.log("WebSocket 연결 성공");
        };

        socket.onmessage = function(event) {
            var receivedMessage = event.data;
            console.log(event);
            console.log("받은 메시지: " + receivedMessage);
           
            // 받은 메시지를 채팅 박스에 추가합니다.
            $("#chatBox").append("<p>" + receivedMessage + "</p>");
        };

        socket.onclose = function(event) {
            console.log("WebSocket 연결 종료");
        };
       
        // 전송 버튼 클릭 시 메시지를 서버로 전송합니다.
        $("#sendButton").click(function() {
            var message = $("#messageInput").val();
            socket.send(message);
            $("#messageInput").val(""); // 입력 필드 초기화
        });
    </script>
</body>
</html>