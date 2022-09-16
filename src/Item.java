import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class Item {
  static int size = Cell.size;
  private Cell loc;
  protected String desc;
  protected BufferedImage image;

  public Item(Cell l, BufferedImage i, String d) {
    loc = l;
    image = i;
    desc = d;
  }

  public void paint(Graphics g) {
    g.drawImage(image, loc.x, loc.y, size, size, null);
  }

  public void setLocation(Cell inLoc) {
    loc = inLoc;
  }

  public Cell getLocation() {
    return loc;
  }
}
