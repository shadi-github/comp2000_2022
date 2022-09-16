import java.awt.image.BufferedImage;

abstract class Surface extends Cell {
  int cost;

  public Surface(int col, int row, BufferedImage img) {
    super(col, row, img);
  }
}
