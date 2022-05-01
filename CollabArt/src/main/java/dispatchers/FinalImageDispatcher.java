package dispatchers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import objects.Artwork;
import objects.Room;
import objects.Rooms;

@WebServlet("/FinalImage")
public class FinalImageDispatcher extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	public FinalImageDispatcher() {}
	
	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String roomcode = request.getParameter("room-code");
    	Room room = Rooms.getRoom(roomcode);
    	Artwork artwork = room.getArtwork();
    	String dataURL = artwork.getCompleted();
    	String statement = artwork.getPrompt().getStatement();
    	
    	response.setContentType("application/json");
    	PrintWriter writer = response.getWriter();
    	JSONObject jsonObject = new JSONObject();
    	
    	jsonObject.put("image-string", dataURL);
    	jsonObject.put("statement", statement);
    	jsonObject.put("players", room.getPlayers());
    	
    	writer.append(jsonObject.toString());
    }
}
