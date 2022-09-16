import java.awt.Color;

public class Fence extends Boundary {
  public Fence(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.MAGENTA;
    description = "Fence";
  }
}
