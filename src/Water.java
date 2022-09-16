import java.awt.Color;

public class Water extends Surface {
  public Water(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.BLUE;
    cost = 100;
    description = "Water";
  }
}
