package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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
       System.out.println(dataURL);
       /*for (Part part: request.getParts()) {
    	   System.out.println(part);
       }
       System.out.println(request.getPart("image-string"));
       System.out.println("done");*/
       
       //800x600 is the size of the entire thing
       //NOTE: Consider changing it to 1000x750?
       //Also: note that the fragment size each user gets is different. Are we goin 
       
       String base64Image = dataURL.split(",")[1];
       byte[] imageBytes = Base64.getDecoder().decode(base64Image);
       //Use bufferedImage to do image transformation: add drawings to background.
       BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

       Graphics2D g2d = image.createGraphics();
       g2d.setColor(new Color(85, 107, 47));
       g2d.fillRect(10, 10, 100, 100);
       g2d.dispose();
       
       
       //Convert back to 64
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       ImageIO.write(image, "png", output);
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