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
	static String check = "SELECT * FROM likes WHERE picId = ? AND username = ?";
	static String remove = "DELETE FROM likes WHERE picID = ? AND username = ?";
	static String update = "UPDATE likes SET likeType = ? WHERE picId = ? AND username = ?";
	static String unsafe = "SET SQL_SAFE_UPDATES = 0";
	static String safe = "SET SQL_SAFE_UPDATES = 1";
	static String picUpdate = "UPDATE drawing SET likes = likes + ? WHERE id = ?";
	static String getLikes = "SELECT * FROM drawing WHERE id = ?";
	
	Likes() {}
	
	public static void Like(int picId, String username) {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (!Check(picId, username)) {
			Insert(picId, username, true);
		} else if (!GetLikeType(picId, username)) {
			Update(picId, username, true);
		}
	}
	
	public static void Dislike(int picId, String username) {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!Check(picId, username)) {
			System.out.println("disliking when like doesn't exist");
			Insert(picId, username, false);
		} else if (GetLikeType(picId, username)) {
			System.out.println("disliking when like already exists");
			Update(picId, username, false);
		}
	}
	
	public static void Unlike(int picId, String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (Check(picId, username)) {
			Remove(picId, username);
		}
	}
	
	public static int GetLike(int picId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(getLikes);){
			ps.setInt(1, picId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt("likes");
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Getting Like Count: " + sqle.getMessage());
			return 0;
		}
	}
	
	public static int DoesExist(int picId, String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		
		return !Check(picId, username) ? 0 : GetLikeType(picId, username) ? 1 : -1;
	}
	
	static void Insert(int picId, String username, boolean likeType) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(insert);) {
    		ps.setInt(1, picId);
    		ps.setString(2, username);
    		ps.setBoolean(3, likeType);
    		ps.executeUpdate();
    			
    		PicUpdateLike(picId, likeType ? 1 : -1);
    			
    	} catch (SQLException sqle) {
    		System.out.println ("SQLException - Inserting Likes: " + sqle.getMessage());
    	}
	}
	
	//returns true if there
	static boolean Check(int picId, String username) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
    			PreparedStatement ps = conn.prepareStatement(check);) {
			ps.setInt(1, picId);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
    	} catch (SQLException sqle) {
    		System.out.println ("SQLException - Checking Like Presence: " + sqle.getMessage());
    		return false;
    	}
	}
	
	static void Update(int picId, String username, boolean likeType) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement pre = conn.prepareStatement(unsafe);
			pre.executeUpdate();
			
			//boolean lastType = GetLikeType(picId, username);
			
			PreparedStatement ps = conn.prepareStatement(update);
			ps.setBoolean(1, likeType);
			ps.setInt(2, picId);
			ps.setString(3, username);
			ps.executeUpdate();
			
			PicUpdateLike(picId, likeType ? 2 : -2);
			
			PreparedStatement post = conn.prepareStatement(safe);
			post.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Updating Like: " + sqle.getMessage());
		}
	}
	
	static void Remove(int picId, String username) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement pre = conn.prepareStatement(unsafe);
			pre.executeUpdate();
			
			PicUpdateLike(picId, GetLikeType(picId, username) ? -1 : 1);
			
			PreparedStatement ps = conn.prepareStatement(remove);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ps.executeUpdate();
			
			PreparedStatement post = conn.prepareStatement(safe);
			post.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Removing Like: " + sqle.getMessage());
		}
	}

	static void PicUpdateLike(int picId, int increment) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement ps = conn.prepareStatement(picUpdate);
			ps.setInt(1, increment);
			ps.setInt(2, picId);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Updating Picture Like Count: " + sqle.getMessage());
		}
	}
	
	static boolean GetLikeType(int picId, String username) {
		try (Connection conn = DriverManager.getConnection(db, user, pwd);) {
			PreparedStatement ps = conn.prepareStatement(check);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getBoolean("likeType");
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Getting LikeType of Like: " + sqle.getMessage());
			return false;
		}
		
	}
}
