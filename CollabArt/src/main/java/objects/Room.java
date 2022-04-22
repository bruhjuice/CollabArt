package objects;

import java.util.ArrayList;
import java.util.List;

public class Room {
	/* The Room class contains all the data associated with a given room */
	
	// The room code of the room
	private String roomCode;
	// Contains a list of all the players, inserted in the order they joined the game
	private List<User> players;
	// Contains the artwork currently associated with this room
	private Artwork artwork;
	// If game has started yet
	private boolean started;
	
	public Room(String roomCode) {
		this.roomCode = roomCode;
		players = new ArrayList<User>();
		artwork = new Artwork();
	}
	
	public String getRoomCode() { return roomCode; }
	public List<User> getPlayers() { return players; }
	public Artwork getArtwork() { return artwork; }
	public boolean isStarted() { return started; }
	
	public void start() {
		started = true;
	}
	
	/** 
	 * User stuff 
	 **/
	public boolean addUser(User user) {
		// Adds the given user to the players list
		// Returns true if added, returns false if already existed
		if (players.contains(user)) return false;
		
		players.add(user);
		return true;
		
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
