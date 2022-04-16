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
import util.Likes;

@WebServlet("/LikeDispatcher")
public class LikeDispatcher extends HttpServlet {
	public LikeDispatcher() {}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String command = request.getParameter("command");
		int picId = 0;
		String username = request.getParameter("username");
		
		try {
			picId = Integer.parseInt(request.getParameter("picId"));
		} catch (NumberFormatException e) {
			System.out.println ("LikeDispatcher Exception: " + e.getMessage());
		}
		
		response.setContentType("text/plain");
	    PrintWriter writer = response.getWriter();
		String output = "";
	    
		if (command.equals("like")) {
			Likes.Like(picId, username);
			output += String.valueOf(Likes.GetLike(picId));
		} else if (command.equals("dislike")) {
			Likes.Dislike(picId, username);
			output += String.valueOf(Likes.GetLike(picId));
		} else if (command.equals("remove")) {
			Likes.Unlike(picId, username);
			output += String.valueOf(Likes.GetLike(picId));
		} else if (command.equals("exist")) {
			output += String.valueOf(Likes.DoesExist(picId, username));
		}
		
		System.out.println(output);
		writer.append(output);
		
    }
}
