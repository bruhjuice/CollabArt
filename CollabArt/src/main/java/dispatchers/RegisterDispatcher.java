package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Helper;
import util.Utility;

import java.sql.*;
import java.io.*;
import java.net.URISyntaxException;
/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/register")
public class RegisterDispatcher extends HttpServlet {

    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	boolean missingData = false;
    	
    	String username = request.getParameter("registerName");
    	String password = request.getParameter("registerPassword");
    	String confirmPassword = request.getParameter("confirmPassword");
    	System.out.println(username);
    	System.out.println(password);
    	System.out.println(confirmPassword);

    	String errorMessage = "yes";
    	
    	if (!password.contentEquals(confirmPassword))
    	{
    	   errorMessage = "different";
    		missingData = true;
    	}
    	if (username == null || username.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!Helper.validName(username))
    	{
    		missingData = true;
    		errorMessage = "invalidUser";
    	}

    	if (password == null || password.contentEquals(""))
    	{
    		missingData = true;
    		//emailError += "<p> email is missing </p>";
    	}
    	if (Helper.nameAlreadyRegistered(username, request, response))
    	{
    		missingData = true;    	
    		errorMessage = "taken";
    	}

    	if (!missingData)
    	{
    		String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
    		// add the jar to tomcat lib if it is not working!

            try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	try (Connection conn = Utility.getConnection();
        			PreparedStatement ps = conn.prepareStatement(sql);) {
        			ps.setString(1, username);
        			ps.setString(2, password);
        			int row = ps.executeUpdate(); //the number of rows affected
        	    	String name="";
        			for (int i = 0; i < username.length(); i++) {
        	            if (username.charAt(i)==' ') {
        	            	name+='=';
        	            }
        	            else {
        	            	name+=username.charAt(i);
        	            }
        	        }
        		
                	Cookie cookie = new Cookie("loggedIn", "true");
                	Cookie cookie2 = new Cookie("playerName", name);
                	cookie.setMaxAge(60 * 60);
                	cookie2.setMaxAge(60 * 60);
                	response.addCookie(cookie);
                	response.addCookie(cookie2);
        			System.out.println(String.format("Row affected %d", row));

        		} catch (SQLException sqle) {
        			System.out.println ("SQLException: " + sqle.getMessage());
        		} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    		//request.getRequestDispatcher("index.jsp").forward(request, response);
        	response.sendRedirect("HomeGallery.jsp");
    		
    		
    	}
    	else
    	{
    		request.setAttribute("regUsername", username);
    		request.setAttribute("regPassword", password);
    		request.setAttribute("error", errorMessage);
    		request.getRequestDispatcher("register.jsp").forward(request, response);

    	}
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