import java.awt.image.BufferedImage;
import java.util.*;

public class Artwork
{
   private String prompt;
   private Coordinates[] playerCoord;
   private String background_image_url;
   private ArrayList<String> fragment_urls;
   
   public Artwork(String prompt, Coordinates[] playerCoord, String background_image_url) {
      this.prompt=prompt;
      this.playerCoord=playerCoord;
      this.background_image_url=background_image_url;
   }
   
   public void addFragment(String frag_url) {
      fragment_urls.add(frag_url);
   }
   
   public BufferedImage getCompleted() {
      //get bufferedImage from background
      //get bufferedImage from fragments
      //piece together completed image
      //return completed;
      return null;
   }
   
}

class Coordinates{
   private int x_start;
   private int y_start;
   private int x_end;
   private int y_end;
   
   public Coordinates(int x_start, int y_start, int x_end, int y_end) {
      this.x_start = x_start;
      this.y_start = y_start;
      this.x_end = x_end;
      this.y_end = y_end;
   }
   
   public int getX_start() {
      return x_start;
   }
   public int getY_start() {
      return y_start;
   }
   public int getX_end() {
      return x_end;
   }
   public int getY_end() {
      return y_end;
   }
}