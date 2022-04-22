package objects;

public class Coordinate{
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
   
   public String toString() {
      StringBuilder s = new StringBuilder();
      s.append("{");
      s.append("\"top\":");
      s.append(top);
      s.append(",");
      s.append("\"left\":");
      s.append(left);
      s.append(",");
      s.append("\"right\":");
      s.append(right);
      s.append(",");
      s.append("\"bottom\":");
      s.append(bottom);
      s.append("}");
      return s.toString();
   }
}