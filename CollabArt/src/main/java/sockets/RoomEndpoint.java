package sockets;
import java.io.IOException;
import java.text.ParseException;

import objects.Coordinate;
import objects.Prompt;
import objects.Room;
import objects.Rooms;
import objects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@ServerEndpoint("/room/{room-code}")
public class RoomEndpoint {
	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig conf, @PathParam("room-code") String roomCode) throws IOException {
		sessions.add(session);
		
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
		System.out.println(message);
		
		// Define common variables
		Map<String, Object> properties = session.getUserProperties();
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
					
					if (!room.isStarted()) {
						// Add new user, if already in rooms array, return error
						int addResult = room.addUser(new User(username));
						if (addResult < 0) {
							System.out.println("addresult: " + addResult);
							if (addResult == -2) jsonResult.put("error", "player-limit-reached");
							else if (addResult == -1) jsonResult.put("error", "username-taken");
							session.getBasicRemote().sendText(jsonResult.toString());
							return;
						};
						
						// Set username prop
						properties.put("username", username);
						
						// Send updated players array
						jsonResult.put("type", "update-players");
						jsonResult.put("players", room.getPlayers());
						sendToRoom(roomCode, jsonResult.toString());
						
					} else {
						// Send prompt to user
					   while(true) {
					      try {
		                     // Set username prop
		                     properties.put("username", username);                 
		                     // If room is started, send the player their prompt
		                     sendPromptToUser(session, room, username);
		                     //If sendPromptToUser works, continue to break;
		                     break;
		                  }catch(Exception e) {
		                     continue;
		                  }   
					   }
					}
					break;
				case "start":
					// Start the room. Once room is started, players are locked in, and cannot leave
					room.start();
					
					// Send a message to players to navigate to game progress page
					jsonResult.put("type", "started");
					sendToRoom(roomCode, jsonResult.toString());
					break;
				case "submitted":
					// Check if artwork has all fragments
					if (room.getArtwork().isCompleted()) {
						System.out.println("COMPLETED!!!");
						
						room.getArtwork().getCompleted();
						
						jsonResult.put("type", "completed");
						sendToRoom(roomCode, jsonResult.toString());
					}
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
    	sessions.remove(session);
    	
    	// Remove user from room
    	String username = (String)session.getUserProperties().get("username");
    	if (username != null && Rooms.roomExists(roomCode)) {
    		Room room = Rooms.getRoom(roomCode);
    		
    		// Only remove user if room has not started yet
    		if (!room.isStarted()) {
	    		room.removeUser(username);
	    		System.out.println(username + " left!");
	    		
	    		// Send updated players array
	    		JSONObject jsonResult = new JSONObject();
	    		jsonResult.put("type", "update-players");
	    		jsonResult.put("players", room.getPlayers());
	    		sendToRoom(roomCode, jsonResult.toString());
    		}
    	}
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // Do error handling here
    	error.printStackTrace();
    }
    
    /* Sends the given message to all people in the given room */
    public static void sendToRoom(String roomCode, String message) {
    	for (Session sess : sessions) {
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
    
    /* Sends the given message to a certain user within a room */
    public static void sendToRoomUser(String roomCode, String username, String message) {
    	for (Session sess : sessions) {
    		String sessRoomCode = (String)sess.getUserProperties().get("room-code");
    		String sessUsername = (String)sess.getUserProperties().get("username");
    		if (
				sess.isOpen() && 
    			sessRoomCode.equals(roomCode) &&
    			sessUsername.equals(username)
			) {
    			try {
					sess.getBasicRemote().sendText(message);
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
    
    /* Send prompts to all users in a room */
    public static void sendPrompts(Session session, Room room) {
    	// Get prompt
		Prompt prompt = room.getArtwork().getPrompt();
		List<String> prompts = prompt.getPrompts();
		List<Coordinate> coordinates = prompt.getCoordinates(); 
		System.out.println(prompt);
		
		// Distribute prompt to each player
		List<User> players = room.getPlayers();
		for (int i = 0; i < players.size(); ++i) {
			JSONObject promptJson = new JSONObject();
			promptJson.put("type", "prompt");
			promptJson.put("prompt", prompts.get(i));
			promptJson.put("bounds", coordinates.get(i));
			sendToRoomUser(room.getRoomCode(), players.get(i).getUsername(), promptJson.toString());
		}
    }
    
    /* Send the correct prompt to the user who should be doing that prompt */
    public static void sendPromptToUser(Session session, Room room, String username) {
    	// Get prompt
    	Prompt prompt = room.getArtwork().getPrompt();
		List<String> prompts = prompt.getPrompts();
		List<Coordinate> coordinates = prompt.getCoordinates(); 
		
		// Send the correct prompt based on username
		int playerNum = room.getPlayerNumber(username);
		JSONObject promptJson = new JSONObject();
		promptJson.put("type", "prompt");
		promptJson.put("prompt", prompts.get(playerNum));
		promptJson.put("bounds", coordinates.get(playerNum));
		sendToRoomUser(room.getRoomCode(), username, promptJson.toString());
    }
}

class TimerWatcher implements Runnable {
	/* Watches the room timer and sends message to room if room timer has changed */
	public void run() {}
}
