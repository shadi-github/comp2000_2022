import java.awt.image.BufferedImage;
import java.awt.Color;

public class Water extends Surface {
  public Water(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.BLUE;
    cost = 100;
    desc = "Water";
  }
}
