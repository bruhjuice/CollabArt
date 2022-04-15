package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

public class Likes {
	static String db = "jdbc:mysql://localhost:3306/CollabArt";
	static String user = Utility.DBUserName;
	static String pwd = Utility.DBPassword;
	
	static String insert = "INSERT INTO likes (picId, username, likeType) VALUES (?, ?, ?)";
	static String check = "SELECT 1 FROM likes WHERE picId = ? AND username = ?";
	static String remove = "DELETE FROM likes WHERE picID = ? AND username = ?";
	static String update = "UPDATE likes SET likeType = ? WHERE picId = ? AND username = ?";
	static String sum = "SELECT SUM(likeType) FROM likes where picId = ?";
	static String unsafe = "SET SQL_SAFE_UPDATES = 0";
	static String safe = "SET SQL_SAFE_UPDATES = 1";
	
	
	public static void Like(int picId, String username) {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!Check(picId, username)) {
			Insert(picId, username, 1);
		} else {
			Update(picId, username, 1);
		}
	}
	
	public static void Dislike(int picId, String username) {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!Check(picId, username)) {
			Insert(picId, username, 1);
		} else {
			Update(picId, username, 1);
		}
	}
	
	public static void Unlike(int picId, String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (Check(picId, username)) {
			Insert(picId, username, -1);
		} else {
			Update (picId, username, -1);
		}
	}
	
	public static int GetLikes(int picId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		return Sum(picId);
	}
	
	public static boolean DoesExist(int picId, String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		return Check(picId, username);
	}
	
	static void Insert(int picId, String username, int likeType) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(insert);) {
    			ps.setInt(1, picId);
    			ps.setString(2, username);
    			ps.setInt(3, likeType);
    			ps.executeUpdate();
    	} catch (SQLException sqle) {
    			System.out.println ("SQLException: " + sqle.getMessage());
    	}
	}
	
	//returns true if there
	static boolean Check(int picId, String username) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			Statement st = conn.createStatement();) {
			ResultSet rs = st.executeQuery(check);
			return rs.getString("username") != "" && rs.getString("username") != null;
    	} catch (SQLException sqle) {
    		System.out.println ("SQLException: " + sqle.getMessage());
    		return false;
    	}
	}
	
	static void Update(int picId, String username, int likeType) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement pre = conn.prepareStatement(unsafe);
			pre.executeUpdate();
			
			PreparedStatement ps = conn.prepareStatement(update);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ps.setInt(3, likeType);
			ps.executeUpdate();
			
			PreparedStatement post = conn.prepareStatement(safe);
			post.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
	}
	
	static void Remove(int picId, String username) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement ps = conn.prepareStatement(remove);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		}
	}
	
	static int Sum(int picId) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement ps = conn.prepareStatement(sum);
			ps.setInt(1, picId);
			ResultSet rs = ps.executeQuery();
			return rs.getInt(picId);
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
			return 0;
		}
	}
}
