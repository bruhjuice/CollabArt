package dispatchers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import objects.Rooms;

@WebServlet("/create-room")
public class CreateRoomDispatcher extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		// Generate a unique room code
		String roomCode = generateRoomCode();
		while (Rooms.roomExists(roomCode)) { roomCode = generateRoomCode(); }
		
		// Create a new room with the generated room code
		Rooms.createRoom(roomCode);
		
		// Return a json object containing the room code
		response.setStatus(200);
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		json.put("roomCode", roomCode);
		
		PrintWriter writer = response.getWriter();
		writer.append(json.toString());
	}
	
	private String generateRoomCode() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 4;
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = (int)(Math.random() * (rightLimit - leftLimit + 1) + leftLimit);
	        buffer.append((char) randomLimitedInt);
	    }
	    return buffer.toString();
	}
}
