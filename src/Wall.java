import java.awt.Color;

public class Wall extends Boundary {
  public Wall(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.DARK_GRAY;
    description = "Wall";
  }
}
