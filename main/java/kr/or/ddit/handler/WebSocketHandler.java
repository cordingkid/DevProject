package kr.or.ddit.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("채팅방 입장");
		sessions.add(session);
		String name = getName(session);
		sendMessageToAll(name + "님이 채팅방에 입장하였습니다.");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 클라이언트로부터 메시지를 수신할 때 호출되는 메서드
		log.info("메세지 입력");
		String receivedMessage = message.getPayload();

		// 예제에서는 수신한 메시지를 그대로 클라이언트에게 전송합니다.
		for (WebSocketSession clientSession : sessions) {
			clientSession.sendMessage(new TextMessage(receivedMessage));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 클라이언트와의 연결이 종료될 때 호출되는 메서드
		log.info("채팅방 아웃");
		sendMessageToAll(getName(session) + "님이 채팅방을 퇴장하였습니다.");
		sessions.remove(session);
	}
	
	private void sendMessageToAll(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(message));
        }
    }

	public String getName(WebSocketSession session) {
		Map<String, Object> map = session.getAttributes();
		String name = (String) map.get("name");
		if(name == null) {
			name = "비로그인 유저";
		}
		return name;
	}
}
