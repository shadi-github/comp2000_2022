import java.awt.image.BufferedImage;
import java.awt.Color;

public class Grass extends Surface {
  public Grass(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.GREEN;
    cost = 20;
    desc = "Grass";
  }
}
