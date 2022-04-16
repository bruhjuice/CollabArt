package sockets;
import java.io.IOException;
import java.text.ParseException;

import objects.Room;
import objects.Rooms;
import objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@ServerEndpoint("/room/{room-code}")
public class RoomEndpoint {
	@OnOpen
	public void onOpen(Session session, EndpointConfig conf, @PathParam("room-code") String roomCode) throws IOException {
		// Set roomCode property
		Map<String, Object> properties = session.getUserProperties();
		properties.put("room-code", roomCode);
		
		// Create json results object
		JSONObject jsonResult = new JSONObject();
		
		// Check if room code exists
		if (!Rooms.roomExists(roomCode)) {
			// Display an error message that room doesn't exist
			jsonResult.put("error", "room-not-found");	
		} else {
			// Room exists, send over all the data associated with the given room
			Room room = Rooms.getRoom(roomCode);
			jsonResult.put("type", "update-players");
			jsonResult.put("players", room.getPlayers());
		}
		
		session.getBasicRemote().sendText(jsonResult.toJSONString());
		//System.out.println(jsonResult.toString());
	}
	
	@OnMessage
    public void onMessage(Session session, String message, @PathParam("room-code") String roomCode) throws IOException {
        // Handle new messages
		//System.out.println(message);
		
		JSONParser parser = new JSONParser();
		JSONObject jsonResult = new JSONObject();
		
		// Check if room code exists
		if (!Rooms.roomExists(roomCode)) {
			// Display an error message that room doesn't exist
			jsonResult.put("error", "room-not-found");	
			session.getBasicRemote().sendText(jsonResult.toString());
		} else {
			// Read other data
			try {
				JSONObject jsonObject = (JSONObject)parser.parse(message);
				
				Room room = Rooms.getRoom(roomCode);
				
				switch ((String)jsonObject.get("type")) {
				case "player-join":
					/* Current player joins the room */
					
					// Get username
					String username = (String)jsonObject.get("username");
					
					// Add new user, if already in rooms array, return error
					if (!room.addUser(new User(username))) {
						jsonResult.put("error", "username-taken");
						session.getBasicRemote().sendText(jsonResult.toString());
						return;
					};
					
					// Set username prop
					Map<String, Object> properties = session.getUserProperties();
					properties.put("username", username);
					
					// Send updated players array
					jsonResult.put("type", "update-players");
					jsonResult.put("players", room.getPlayers());
					sendToRoom(session, roomCode, jsonResult.toString());
					break;
				}
				
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonResult.put("error", "server-error");
				session.getBasicRemote().sendText(jsonResult.toString());
			}
		}
    }

    @OnClose
    public void onClose(Session session, CloseReason reason, @PathParam("room-code") String roomCode) throws IOException {
        // WebSocket connection closes
    	
    	// Remove user from room
    	String username = (String)session.getUserProperties().get("username");
    	if (username != null && Rooms.roomExists(roomCode)) {
    		Room room = Rooms.getRoom(roomCode);
    		room.removeUser(username);
    		System.out.println(username + " left!");
    		
    		// Send updated players array
    		JSONObject jsonResult = new JSONObject();
    		jsonResult.put("type", "update-players");
    		jsonResult.put("players", room.getPlayers());
    		sendToRoom(session, roomCode, jsonResult.toString());
    	}
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // Do error handling here
    	error.printStackTrace();
    }
    
    private void sendToRoom(Session session, String roomCode, String message) {
    	for (Session sess : session.getOpenSessions()) {
    		String sessRoomCode = (String)sess.getUserProperties().get("room-code");
    		if (
				sess.isOpen() && 
    			sessRoomCode.equals(roomCode)
			) {
    			try {
					sess.getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
}
