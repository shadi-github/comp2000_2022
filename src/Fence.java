import java.awt.image.BufferedImage;
import java.awt.Color;

public class Fence extends Boundary {
  public Fence(int col, int row, BufferedImage img) {
    super(col, row, img);
    color = Color.MAGENTA;
    desc = "Fence";
  }
}
