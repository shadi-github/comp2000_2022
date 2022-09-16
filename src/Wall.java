import java.awt.image.BufferedImage;
import java.awt.Color;

public class Wall extends Boundary {
  public Wall(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.DARK_GRAY;
    desc = "Wall";
  }
}
