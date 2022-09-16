import java.awt.image.BufferedImage;
import java.awt.Color;

public class Sand extends Surface {
  public Sand(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.YELLOW;
    cost = 50;
    desc = "Sand";
  }
}
