package objects;

import java.util.ArrayList;
import java.util.List;

public class Room {
	/* The Room class contains all the data associated with a given room */
	
	// Contains a list of all the players, inserted in the order they joined the game
	private List<User> players;
	// Contains the artwork currently associated with this room
	private Artwork artwork;
	
	public Room() {
		players = new ArrayList<User>();
		artwork = new Artwork();
	}
	
	public List<User> getPlayers() { return players; }
	public Artwork getArtwork() { return artwork; }
	
	public boolean addUser(User user) {
		// Adds the given user to the players list
		// Returns true if added, returns false if already existed
		if (players.contains(user)) return false;
		
		players.add(user);
		return true;
		
	}
	
	public void removeUser(User user) {
		// Removes the given user from the players list
		removeUser(user.getId());
	}
	
	public void removeUser(String userId) {
		// Removes the given user based on their userId
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).getId() == userId) {
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
		return getPlayerNumber(user.getId());
	}
	
	public int getPlayerNumber(String userId) {
		// Gets the player number of the given user based on their userId
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).getId() == userId) {
				return i+1;
			}
		}
		return -1;
	}
}
