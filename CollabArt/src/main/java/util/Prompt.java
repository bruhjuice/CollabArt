package util;

public class Prompt{
   String statement;
   String[] prompts;
   
   Coordinate[] coordinates;
   String background_image_path;
   
   public String getStatement(){
      return statement;
   }
   public String[] prompts(){
      return prompts;
   }
   
   public Coordinate[] getCoordinates() {
      return coordinates;
   }
   public String getBackgroundImage(){
      return background_image_path;
   }
}

class Coordinate{
   private int top;
   private int left;
   private int bottom;
   private int right;

   public Coordinate(int top, int left, int bottom, int right) {
      this.top = top;
      this.left = left;
      this.bottom = bottom;
      this.right = right;
   }
   
   public int getTop() {
      return top;
   }
   public int getLeft() {
      return left;
   }
   public int getBottom() {
      return bottom;
   }
   public int getRight() {
      return right;
   }
}
