import java.awt.image.BufferedImage;
import java.awt.Color;

public class Stone extends Surface {
  public Stone(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.LIGHT_GRAY;
    cost = 10;
    desc = "Stone";
  }
}
