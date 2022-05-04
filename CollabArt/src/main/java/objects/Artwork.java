package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.*;

public class Artwork
{
   Prompt prompt;
   private String[] fragment_urls;
   //String of all users, comma 
   private Room room;
   //dataURL of completed image. If empty string, means it has not been created yet.
   //Since 4 users submit their fragment & call fragment dispatcher, once all 4 fragment urls have to submitted, 
   // we only need one of them to assemble the completedURL; they will also add the image to the database.
   // The others can just grab this string.
   private String completed_image;
   
   public Artwork(Room room) {
      prompt = PromptPool.getPrompt();
      fragment_urls = new String[4];
      completed_image=null;
      this.room = room;
   }
   
   public Prompt getPrompt() {
	   return prompt;
   }
   
   public void addFragment(String frag_url, int player_no) {
      fragment_urls[player_no]=frag_url;
   }
   
   public boolean isCompleted() {
	   for (int i = 0; i < fragment_urls.length; ++i) {
		   if (fragment_urls[i] == null) {
			   return false;
		   }
	   }
	   return true;
   }
   
   public String getCompleted() {
	   if (completed_image != null) {
		   System.out.println("already completed the image");
		   return completed_image;
	   }
	   /* Returns the compiled base64 string */
	   
      //get bufferedImage from background
      //get bufferedImage from fragments
      //piece together completed image
      //return completed
      
      Prompt prompt = getPrompt();
      String backgroundImage = prompt.getBackgroundImage();
      ArrayList<Coordinate> coordinates = prompt.getCoordinates();
      
      InputStream inputStream = Artwork.class.getResourceAsStream("../"+backgroundImage);
      
      //Start drawing entire image in proper size.
      //First read in background image
      BufferedImage bgImage = null;
      BufferedImage rescaleImage = null;
      try {
         bgImage = ImageIO.read(inputStream);
         rescaleImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
      } catch (IOException e) {
         e.printStackTrace();
      }
      //Draw background image
      Graphics2D g2d = rescaleImage.createGraphics();
      
      //Parameters for drawImage: image, xstart, ystart, width, height    
      //Draw background image to scaled image
      g2d.drawImage(bgImage, 0, 0, 800, 600, null);
      
      BufferedImage image = null;
      //for each user, draw their fragment
      for(int playerNo=0; playerNo<4; playerNo++) {
         int left = coordinates.get(playerNo).getLeft();
         int top = coordinates.get(playerNo).getTop();
         int right = coordinates.get(playerNo).getRight();
         int bottom = coordinates.get(playerNo).getBottom();
         
         if (fragment_urls[playerNo] != null) {
	         String base64Image = fragment_urls[playerNo].split(",")[1];
	         byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	         //Use bufferedImage to do image transformation: add drawings to background.
	         try
	         {
	            image = ImageIO.read(new ByteArrayInputStream(imageBytes));
	         } catch (IOException e)
	         {
	            e.printStackTrace();
	         }     
	         g2d.drawImage(image, left, top, right-left, bottom-top, null);      
         }
      }
      
      g2d.dispose();
      
      //Convert finished back to base64
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      try
      {
         ImageIO.write(rescaleImage, "png", output);
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      completed_image = Base64.getEncoder().encodeToString(output.toByteArray());
      
      //System.out.println("COMPLETED IMAGE: " + completed_image);
      
      //Add drawing entry to database
      String sql = "INSERT INTO drawings (image, likes, dateCreated, createdUsers, prompt) VALUES (?, 0, ?, ?, ?)";
      
      try
      {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }

      try (Connection conn = Utility.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);)
      {
         //Note: update database so that image's varchar length is big enough
         ps.setString(1, completed_image);
         ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
         //Get users (note update database field name: all users!)
         
         String usersString = "";
         //add each user's username
         for(User user : room.getPlayers()) {
            usersString+=user.getUsername()+", ";
         }
         usersString=usersString.substring(0, usersString.length()-2);
         ps.setString(3, usersString);
         ps.setString(4, prompt.getStatement());
         
         int row = ps.executeUpdate(); // the number of rows affected
         
         System.out.println(String.format("Row affected %d", row));

      } catch (SQLException sqle)
      {
         System.out.println("SQLException: " + sqle.getMessage());
      } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      return completed_image;
   }
   
}

