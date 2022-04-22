package objects;
import java.util.ArrayList;

public class Prompt{
   String statement;
   ArrayList<String> prompts;
   
   ArrayList<Coordinate> coordinates;
   String background_image_path;
   
   public Prompt(String statement, ArrayList<String> prompts, ArrayList<Coordinate> coordinates, String bg) {
      this.statement=statement;
      this.prompts=prompts;
      this.coordinates=coordinates;
      this.background_image_path=bg;
   }
   
   public String getStatement(){
      return statement;
   }
   public ArrayList<String> getPrompts(){
      return prompts;
   }
   
   public ArrayList<Coordinate> getCoordinates() {
      return coordinates;
   }
   public String getBackgroundImage(){
      return background_image_path;
   }
   
   public String toString() {
      return "PromptObject: "+statement+", "+prompts+", "+coordinates+", "+background_image_path;
   }
}
