import java.awt.Color;

public class Stone extends Surface {
  public Stone(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.LIGHT_GRAY;
    cost = 10;
    description = "Stone";
  }
}
