package objects;
import java.util.HashMap;
import java.util.Map;

public class Rooms {
	private static Map<String, Room> rooms = new HashMap<String, Room>();
	
	public static void createRoom(String roomCode) {
		//System.out.println("Creating room " + roomCode);
		rooms.put(roomCode, new Room(roomCode));
	}
	
	public static boolean roomExists(String roomCode) {
		//System.out.println("ROOMS: " + rooms.keySet() + ", roomCode: " + roomCode);
		return rooms.containsKey(roomCode);
	}
	
	public static Room getRoom(String roomCode) {
		return rooms.get(roomCode);
	}
}
