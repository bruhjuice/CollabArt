package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
       Prompt prompt = artwork.getPrompt();
       String backgroundImage = prompt.getBackgroundImage();
       ArrayList<Coordinate> coordinates = prompt.getCoordinates();
       int left = coordinates.get(playerNo).getLeft();
       int top = coordinates.get(playerNo).getTop();
       int right = coordinates.get(playerNo).getRight();
       int bottom = coordinates.get(playerNo).getBottom();
       
       
       System.out.println(dataURL);
       
       String base64Image = dataURL.split(",")[1];
       byte[] imageBytes = Base64.getDecoder().decode(base64Image);
       //Use bufferedImage to do image transformation: add drawings to background.
       BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
       
       String usingSystemProperty = System.getProperty("user.dir");
       System.out.println("Current directory path using system property:- " + usingSystemProperty);
       
       InputStream inputStream = FragmentDispatcher.class.getResourceAsStream("../"+backgroundImage);
       
       BufferedImage bgImage = null;
       BufferedImage rescaleImage = null;
       try {
          bgImage = ImageIO.read(inputStream);
          rescaleImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
       } catch (IOException e) {
          e.printStackTrace();
       }
       Graphics2D g2d = rescaleImage.createGraphics();
       
       //Parameters for drawImage: image, xstart, ystart, width, height       
       g2d.drawImage(bgImage, 0, 0, 800, 600, null);
       g2d.drawImage(image, left, top, right-left, bottom-top, null);
       g2d.dispose();
       
       //Convert back to base64
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       ImageIO.write(rescaleImage, "png", output);
       String completeString = Base64.getEncoder().encodeToString(output.toByteArray());
       
       response.setContentType("text/plain");
       PrintWriter writer = response.getWriter();
       writer.append(completeString);
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