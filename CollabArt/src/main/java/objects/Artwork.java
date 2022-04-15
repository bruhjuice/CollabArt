package objects;
import java.awt.image.BufferedImage;
import util.*;

public class Artwork
{
   Prompt prompt;
   private String[] fragment_urls;
   private String completed_Image;
   
   //default constructor, randomly pulls from pool;
   
   
   public Artwork() {
	  prompt = PromptPool.getPrompt();
      fragment_urls = new String[4];
   }
   public Artwork(Prompt prompt, String background_image_url) {
      this.prompt=prompt;
      fragment_urls = new String[4];
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

