package util;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

public class Likes {
	
	static String insert = "INSERT INTO likes (picId, username, likeType) VALUES (?, ?, ?)";
	static String check = "SELECT * FROM likes WHERE picId = ? AND username = ?";
	static String remove = "DELETE FROM likes WHERE picID = ? AND username = ?";
	static String update = "UPDATE likes SET likeType = ? WHERE picId = ? AND username = ?";
	static String unsafe = "SET SQL_SAFE_UPDATES = 0";
	static String safe = "SET SQL_SAFE_UPDATES = 1";
	static String picUpdate = "UPDATE drawings SET likes = likes + ? WHERE id = ?";
	static String getLikes = "SELECT * FROM drawings WHERE id = ?";
	static String recalculate = "SELECT COUNT(*) FROM likes WHERE picId = ? AND likeType = ?";
	static String setLikes = "UPDATE drawings SET likes = ? WHERE id = ?";
	
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
	
	static void Recalculate(int picId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = Utility.getConnection()){
			PreparedStatement likes = conn.prepareStatement(recalculate);
			likes.setInt(1, picId);
			likes.setBoolean(2, true);
			
			PreparedStatement dislikes = conn.prepareStatement(recalculate);
			dislikes.setInt(1, picId);
			dislikes.setBoolean(2, false);
			
			ResultSet likeRs = likes.executeQuery();
			ResultSet dislikeRs = dislikes.executeQuery();
			likeRs.next();
			dislikeRs.next();
			
			System.out.println("Getting Values");
			
			int value = likeRs.getInt(1) - dislikeRs.getInt(1);
			SetDrawingLikes(picId, value);
			
		} catch (SQLException sqle) {
			System.out.println("SQLException - Recalculating: " + sqle.getMessage());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int GetLike(int picId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		
		try (Connection conn = Utility.getConnection();
    			PreparedStatement ps = conn.prepareStatement(getLikes);){
			ps.setInt(1, picId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt("likes");
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Getting Like Count: " + sqle.getMessage());
			return 0;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
		try (Connection conn = Utility.getConnection();
    			PreparedStatement ps = conn.prepareStatement(insert);) {
    		ps.setInt(1, picId);
    		ps.setString(2, username);
    		ps.setBoolean(3, likeType);
    		ps.executeUpdate();
    		Recalculate(picId);
    		//PicUpdateLike(picId, likeType ? 1 : -1);
    			
    	} catch (SQLException sqle) {
    		System.out.println ("SQLException - Inserting Likes: " + sqle.getMessage());
    	} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	//returns true if there
	static boolean Check(int picId, String username) {
		try (Connection conn = Utility.getConnection();
    			PreparedStatement ps = conn.prepareStatement(check);) {
			ps.setInt(1, picId);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
    	} catch (SQLException sqle) {
    		System.out.println ("SQLException - Checking Like Presence: " + sqle.getMessage());
    		return false;
    	} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return false;
      }
	}
	
	static void Update(int picId, String username, boolean likeType) {
		try (Connection conn = Utility.getConnection();) {
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
		} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	static void Remove(int picId, String username) {
		try (Connection conn = Utility.getConnection();) {
			PreparedStatement pre = conn.prepareStatement(unsafe);
			pre.executeUpdate();
			
			PreparedStatement ps = conn.prepareStatement(remove);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ps.executeUpdate();
			
			Recalculate(picId);
			
			PreparedStatement post = conn.prepareStatement(safe);
			post.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Removing Like: " + sqle.getMessage());
		} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}

	static void PicUpdateLike(int picId, int increment) {
		try (Connection conn = Utility.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(picUpdate);
			ps.setInt(1, increment);
			ps.setInt(2, picId);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Updating Picture Like Count: " + sqle.getMessage());
		} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	static boolean GetLikeType(int picId, String username) {
		try (Connection conn = Utility.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(check);
			ps.setInt(1, picId);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getBoolean("likeType");
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Getting LikeType of Like: " + sqle.getMessage());
			return false;
		} catch (URISyntaxException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return false;
      }
		
	}
	
	static void SetDrawingLikes(int picId, int value) {
		try (Connection conn = Utility.getConnection();) {
			PreparedStatement ps = conn.prepareStatement(setLikes);
			ps.setInt(1, value);
			ps.setInt(2, picId);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException - Setting Drawing Likes: " + sqle.getMessage());
		} catch (URISyntaxException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
		}
	}
}
