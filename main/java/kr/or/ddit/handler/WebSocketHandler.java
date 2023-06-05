package kr.or.ddit.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new ArrayList<>();
//	private Map<String, WebSocketSession> userMap = new HashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
		
		
//		log.info("누구? :" + (String)httpSession.getAttribute("name"));
		// 클라이언트와의 연결이 성립될 때 호출되는 메서드
		log.info("채팅방 입장");
		sessions.add(session);
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
		sessions.remove(session);
	}
}
