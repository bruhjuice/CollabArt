package util;

public class Prompt{
   String statement;
   String[] prompts;
   
   Coordinate[] coordinates;
   String background_image_path;
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
   
   public int getX_start() {
      return top;
   }
   public int getY_start() {
      return left;
   }
   public int getX_end() {
      return bottom;
   }
   public int getY_end() {
      return right;
   }
}
