import java.awt.image.BufferedImage;

abstract class Boundary extends Cell {
  public Boundary(int col, int row, BufferedImage img) {
    super(col, row, img);
  }
}
