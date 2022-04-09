package dispatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Helper;

import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/login")
public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	boolean missingData = false;

    	String username = request.getParameter("loginEmail");
    	String password = request.getParameter("loginPassword");

    	if (username == null || username.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (password == null || password.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!missingData)
    	{

    
    	try {
			if(!Helper.checkPassword(username, password))
				{
					missingData = true;
				}
			else
			{

				Cookie cookie = new Cookie("loggedIn", "true");
            	cookie.setMaxAge(60 * 60);
            	response.addCookie(cookie);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}


    		if (!missingData)
    		{
    			System.out.println("login Success");
    			response.sendRedirect("HomeGallery.jsp");
    			
    		}
    		else
    		{
        		System.out.println("Login Failed!");
        		request.setAttribute("error", "yes");
        		request.setAttribute("username", username);
        		request.setAttribute("password", password);
        		request.getRequestDispatcher("login.jsp").forward(request, response);
    		}
    	

        //TODO
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
