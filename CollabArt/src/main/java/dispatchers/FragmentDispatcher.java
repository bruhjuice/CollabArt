package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;

import objects.Artwork;
import objects.Coordinate;
import objects.Prompt;
import objects.Room;
import objects.Rooms;

import java.io.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;


/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/Fragment")
public class FragmentDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FragmentDispatcher() {
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
      
       //Will we need Threads?
       //needs dataurl, player_no, artwork object
       //artworkobject.addFragment(dataurl, playerno)
       //After all has been inserted (need to wait for that)
       //Call getCompleted() (only the first call needs to do the work of assembling the image together & adding to database)
       // Rest just get the returned dataString.
       
       String dataURL=request.getParameter("image-string");
       String username=request.getParameter("username");
       String roomcode = request.getParameter("room-code");
       Room room = Rooms.getRoom(roomcode);
       int playerNo = room.getPlayerNumber(username);
       Artwork artwork = room.getArtwork();
       
       artwork.addFragment(dataURL, playerNo);
       
       response.setContentType("application/json");
       PrintWriter writer = response.getWriter();
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("success", true);
       writer.append(jsonObject.toString());
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }*/

}