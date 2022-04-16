package objects;
import java.awt.image.BufferedImage;
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
   
   public void addFragment(String frag_url, int player_no) {
      fragment_urls[player_no-1]=frag_url;
   }
   
   public BufferedImage getCompleted() {
      //get bufferedImage from background
      //get bufferedImage from fragments
      //piece together completed image
      //return completed;
      return null;
   }
   
}

