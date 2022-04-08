import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.simple.JSONObject;

@ServerEndpoint("/room/{roomCode}")
public class RoomEndpoint {
	@OnOpen
	public void onOpen(Session session, EndpointConfig conf, @PathParam("roomCode") String roomCode) throws IOException {
		JSONObject json = new JSONObject();
		// Check if room code exists
		if (false /* room code doesn't exist */) {
			json.put("error", "room-not-found");	
		} else {
			List<String> players = new ArrayList<String>();
			players.add("max");
			players.add("jony");
			players.add("elliot");
			json.put("players", players);
		}
		
		session.getBasicRemote().sendText(json.toJSONString());
		System.out.println(json.toString());
	}
	
	@OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
		System.out.println(message);
		session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // Do error handling here
    }
}
