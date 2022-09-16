import java.awt.Color;

public class Grass extends Surface {
  public Grass(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.GREEN;
    cost = 20;
    description = "Grass";
  }
}
