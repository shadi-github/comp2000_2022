import java.awt.Color;

public class Sand extends Surface {
  public Sand(char col, int row, int x, int y) {
    super(col, row, x, y);
    color = Color.YELLOW;
    cost = 50;
    description = "Sand";
  }
}
