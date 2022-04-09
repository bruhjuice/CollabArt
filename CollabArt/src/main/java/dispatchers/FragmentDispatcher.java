package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.io.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Base64;


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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
      
      //Thread?
       //needs dataurl, player_no, artowrk object
       //artworkobject.addFragment(dataurl, playerno)
       //Afrer all has been inserted
       //Class getCompleted()
       
       String dataURL=request.getParameter("image-string");
       //800x600 fragment
       
       String base64Image = dataURL.split(",")[1];
       byte[] imageBytes = Base64.getDecoder().decode(base64Image);
       
       BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
       
       request.setAttribute("image", image);
       
       request.getRequestDispatcher("gameEnd.jsp").forward(request, response); 
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}