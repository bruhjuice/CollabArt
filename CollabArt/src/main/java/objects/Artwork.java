package objects;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import dispatchers.FragmentDispatcher;
import util.*;

public class Artwork
{
   Prompt prompt;
   private String[] fragment_urls;
   //dataURL of completed image. If empty string, means it has not been created yet.
   //Since 4 users submit their fragment & call fragment dispatcher, once all 4 fragment urls have to submitted, 
   // we only need one of them to assemble the completedURL; they will also add the image to the database.
   // The others can just grab this string.
   private String completed_image;
   
   //default constructor, randomly pulls from pool;
   
   
   public Artwork() {
	  prompt = PromptPool.getPrompt();
      fragment_urls = new String[4];
      completed_image="";
   }
   public Artwork(Prompt prompt, String background_image_url) {
      this.prompt=prompt;
      fragment_urls = new String[4];
      completed_image="";
   }
   
   public Prompt getPrompt() {
	   return prompt;
   }
   
   public void addFragment(String frag_url, int player_no) {
      fragment_urls[player_no]=frag_url;
   }
   
   public BufferedImage getCompleted() {
      //get bufferedImage from background
      //get bufferedImage from fragments
      //piece together completed image
      //return completed;
      
      Prompt prompt = getPrompt();
      String backgroundImage = prompt.getBackgroundImage();
      ArrayList<Coordinate> coordinates = prompt.getCoordinates();
      
      
      String usingSystemProperty = System.getProperty("user.dir");
      System.out.println("Current directory path using system property:- " + usingSystemProperty);
      
      InputStream inputStream = Artwork.class.getResourceAsStream("../"+backgroundImage);
      
      BufferedImage bgImage = null;
      BufferedImage rescaleImage = null;
      try {
         bgImage = ImageIO.read(inputStream);
         rescaleImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
      } catch (IOException e) {
         e.printStackTrace();
      }
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
         
         String base64Image = fragment_urls[playerNo].split(",")[1];
         byte[] imageBytes = Base64.getDecoder().decode(base64Image);
         //Use bufferedImage to do image transformation: add drawings to background.
         try
         {
            image = ImageIO.read(new ByteArrayInputStream(imageBytes));
         } catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         g2d.drawImage(image, left, top, right-left, bottom-top, null);
         
      }
      
      
      g2d.dispose();
      
      //Convert back to base64
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      try
      {
         ImageIO.write(rescaleImage, "png", output);
      } catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      completed_image = Base64.getEncoder().encodeToString(output.toByteArray());
      
      //Can change so that it returns a bufferedimage instead of null. Alternatively, make getCompleted return a string
      return null;
   }
   
}

