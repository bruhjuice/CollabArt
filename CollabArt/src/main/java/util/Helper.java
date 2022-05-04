package util;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {
    /**
     * check if name is valid
     *
     * @param name the name user provides
     * @return valid or not valid
     */
    public static boolean validName(String name) {
        return Utility.namePattern.matcher(name).matches();
    }


    /**
     * Get username with the email
     *
     * @param email
     * @return userName
     * @throws SQLException
     */
    public static String getUserName(String email) throws SQLException {

		String sql = "SELECT username FROM Users WHERE email=?";
		String username = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = Utility.getConnection();
				  PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
				{
					username = rs.getString("username");

				
				}
			} catch (SQLException ex) {
				System.out.println ("SQLException: " + ex.getMessage());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        //TODO
    	
        return username;
    }



    /**
     * check if the email and password matches
     *
     * @param email
     * @param password
     */
    public static boolean checkPassword(String username, String password) throws SQLException {
		String sql = "SELECT password FROM Users WHERE username=?";
		
		String selectedPassword = "";
		// add the jar to tomcat lib if it is not working!
        try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = Utility.getConnection();
				  PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
				{
					selectedPassword = rs.getString("password");
				}
			} catch (SQLException ex) {
				System.out.println ("SQLException: " + ex.getMessage());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		
    	
        return (selectedPassword.equals(password));
    }

    /**
     * Check if email is already registered

     */
    public static boolean nameAlreadyRegistered(String username, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		String sql = "SELECT username FROM Users WHERE username = (?)";
		String selectedName = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Connection conn = Utility.getConnection();
				  PreparedStatement ps = conn.prepareStatement(sql);) {
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
				{
					selectedName = rs.getString("username");

				
				}
			} catch (SQLException ex) {
				System.out.println ("SQLException: " + ex.getMessage());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		System.out.println("selected" + selectedName);
		System.out.println(username);

		if (selectedName.contentEquals(username))
		{
	    	System.out.println("email already registered");

			return true;
		}
		else
		{	
	    	System.out.println("creating new ");
			return false;
		}
    }
}
