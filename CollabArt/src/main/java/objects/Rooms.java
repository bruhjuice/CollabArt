package objects;
import java.util.HashMap;
import java.util.Map;

public class Rooms {
	private static Map<String, Room> rooms = new HashMap<String, Room>();
	
	public static void createRoom(String roomCode) {
		rooms.put(roomCode, new Room());
	}
	
	public static boolean roomExists(String roomCode) {
		return rooms.containsKey(roomCode);
	}
}
