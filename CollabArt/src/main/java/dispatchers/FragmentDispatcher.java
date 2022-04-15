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
       
       
       String base64Image = dataURL.split(",")[1];
       byte[] imageBytes = Base64.getDecoder().decode(base64Image);
       //Use bufferedImage to do image transformation: add drawings to background.
       BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
       
       String usingSystemProperty = System.getProperty("user.dir");
       System.out.println("Current directory path using system property:- " + usingSystemProperty);
       
       //InputStream inputStream = FragmentDispatcher.class.getResourceAsStream("src/main/webapp/images/farm.jpg");
       
       BufferedImage bgImage = null;
       try {
          //Unable to read input file from images folder for some reason, unless I use absolute path: won't work on other computers
          //In your run configurations => arguments, check your working directory.
           bgImage = ImageIO.read(new File("/Users/Elliott/Desktop/USC/usc_spring_2022/cs201/CollabArt/CollabArt/src/main/webapp/images/farm.jpg"));
          //bgImage = ImageIO.read(inputStream);
       } catch (IOException e) {
          e.printStackTrace();
       }
       
       Graphics2D g2d = bgImage.createGraphics();
       /*
       g2d.setColor(new Color(85, 107, 47));
       g2d.fillRect(10, 10, 100, 100);
       g2d.dispose();
       */
       
       //Parameters: image, xstart, ystart, width, height
       g2d.drawImage(image, 30, 30, 320, 240, null);
       g2d.dispose();
       
       //Convert back to base64
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       ImageIO.write(bgImage, "png", output);
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