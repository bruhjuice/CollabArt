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

    	String name = request.getParameter("registerName");
    	String password = request.getParameter("registerPassword");
    	String confirmPassword = request.getParameter("confirmPassword");
    	System.out.println(name);
    	System.out.println(password);
    	System.out.println(confirmPassword);

    	if (!password.contentEquals(confirmPassword))
    	{
    		missingData = true;
    	}
    	if (name == null || name.contentEquals(""))
    	{
    		missingData = true;
    	}
    	if (!Helper.validName(name))
    	{
    		missingData = true;
    		
    	}

    	if (password == null || password.contentEquals(""))
    	{
    		missingData = true;
    		//emailError += "<p> email is missing </p>";
    	}
    	if (Helper.nameAlreadyRegistered(name, request, response))
    	{
    		missingData = true;
    	}


    	



    	

    	if (!missingData)
    	{
    		String db = "jdbc:mysql://localhost:3306/CollabArt";
    		String user = Utility.DBUserName;
    		String pwd = Utility.DBPassword;
    		String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
    		// add the jar to tomcat lib if it is not working!

            try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	try (Connection conn = DriverManager.getConnection(db, user, pwd);
        			PreparedStatement ps = conn.prepareStatement(sql);) {
        			ps.setString(1, name);
        			ps.setString(2, password);
        			int row = ps.executeUpdate(); //the number of rows affected

                	Cookie cookie = new Cookie("loggedIn", "true");
                	cookie.setMaxAge(60 * 60);
                	response.addCookie(cookie);
        			System.out.println(String.format("Row affected %d", row));

        		} catch (SQLException sqle) {
        			System.out.println ("SQLException: " + sqle.getMessage());
        		}

    		//request.getRequestDispatcher("index.jsp").forward(request, response);
        	response.sendRedirect("HomeGallery.jsp");
    		
    		
    	}
    	else
    	{
    		request.setAttribute("regUsername", name);
    		request.setAttribute("regPassword", password);
    		request.setAttribute("error", "yes");
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