package objects;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.json.simple.JSONObject;

import sockets.RoomEndpoint;

public class Room {
	/* The Room class contains all the data associated with a given room */
	
	public static final int MAX_PLAYERS = 4;
	
	// The room code of the room
	private String roomCode;
	// Contains a list of all the players, inserted in the order they joined the game
	private List<User> players;
	// Contains the artwork currently associated with this room
	private Artwork artwork;
	// If game has started yet
	private boolean started;
	// Current time left of room
	private int timeLeft; 
	// Lock? + condition?
	
	public Room(String roomCode) {
		this.roomCode = roomCode;
		players = new ArrayList<User>();
		artwork = new Artwork(this);
	}
	
	public String getRoomCode() { return roomCode; }
	public List<User> getPlayers() { return players; }
	public Artwork getArtwork() { return artwork; }
	public boolean isStarted() { return started; }
	public int getTimeLeft() { return timeLeft; }
	
	public void decreaseTimeLeft() { 
		timeLeft--; 
		
		// Send the current time to the users
		JSONObject jsonResult = new JSONObject();
	    jsonResult.put("type", "timer");
	    jsonResult.put("time-left", timeLeft);
	    RoomEndpoint.sendToRoom(roomCode, jsonResult.toString());
		
		if (timeLeft <= 0) {
			// end game!
			jsonResult = new JSONObject();
			jsonResult.put("type", "completed");
			RoomEndpoint.sendToRoom(roomCode, jsonResult.toString());
		}
	}
	
	public void start() {
		started = true;
		timeLeft = 60;
		Thread timerThread = new Thread(new Timer(this));
		timerThread.start();
	}
	
	/** 
	 * User stuff 
	 **/
	public int addUser(User user) {
		// Adds the given user to the players list
		// Returns 0 if added, returns negative number if there's an error
		if (players.size() >= MAX_PLAYERS) return -2;
		if (players.contains(user)) return -1;
		
		players.add(user);
		return 0;
	}
	
	public void removeUser(User user) {
		// Removes the given user from the players list
		removeUser(user.getUsername());
	}
	
	public void removeUser(String username) {
		// Removes the given user based on their userId
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).getUsername().equals(username)) {
				players.remove(i);
				return;
			}
		}
	}
	
	public User getHost() {
		// Gets the user that is the host of the current game
		return players.get(0);
	}
	
	public int getPlayerNumber(User user) {
		// Gets the player number of the given user
		return getPlayerNumber(user.getUsername());
	}
	
	public int getPlayerNumber(String username) {
		// Gets the player number of the given user based on their username
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).getUsername().equals(username)) {
				return i;
			}
		}
		return -1;
	}
}

class Timer implements Runnable {
	private Room room;
	public Timer(Room room) {
		this.room = room;
	}
	
	public void run() {
		try {
			while (room.getTimeLeft() > 0) {
				Thread.sleep(1000);
				room.decreaseTimeLeft();
			}
			/*
			for (int i = 0; i <= time; ++i) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type", "timer");
				jsonObject.put("time-left", time - i);
				RoomEndpoint.sendToRoom(session, roomCode, jsonObject.toString());
				Thread.sleep(1000);
			}*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
