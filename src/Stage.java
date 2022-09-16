import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class Stage {
  Grid grid;
  Optional<Point> prevLoc = Optional.empty();
  Instant prevTime = Instant.now();

  public Stage() {
    grid = new Grid();
  }

  public void paint(Graphics g, Point mouseLoc) {
    int ttDelay = 1000;
    int ttWidth = 100;
    int ttHeight = 80;
    boolean displayTooltip = false;
    Instant rightNow = Instant.now();
    if(mouseLoc == null) {
      prevLoc = Optional.empty();
      prevTime = rightNow;
    } else if(prevLoc.isEmpty()) {
      prevLoc = Optional.of(mouseLoc);
      prevTime = rightNow;
    } else if(!mouseLoc.equals(prevLoc.get())) {
      prevLoc = Optional.of(mouseLoc);
      prevTime = rightNow;
    } else if(Duration.between(prevTime, rightNow).toMillis() < ttDelay) {
      displayTooltip = false;
    } else {
      displayTooltip = true;
    }
    grid.paint(g, mouseLoc);
    Optional<Cell> underMouse = grid.cellAtPoint(mouseLoc);
    if(underMouse.isPresent() && displayTooltip) {
      Cell hoverCell = underMouse.get();
      int tooltipX = hoverCell.x;
      int tooltipY = hoverCell.y;
      if(tooltipX > 720-100) {
        tooltipX = 720-100;
      }
      if(tooltipY > 720-80) {
        tooltipY = 720-80;
      }
      g.setColor(Color.WHITE);
      g.fillRect(tooltipX, tooltipY, ttWidth, ttHeight);
      g.setColor(Color.BLACK);
      g.drawString(hoverCell.col + Integer.toString(hoverCell.row), tooltipX+10, tooltipY+20);
      g.drawString(hoverCell.description, tooltipX+10, tooltipY+40);
      Optional<Integer> crossingTime = Optional.empty();
      try {
        Surface surfaceCell = (Surface) hoverCell;
        crossingTime = Optional.of(surfaceCell.cost);
      } catch (Exception e) {}
      if(crossingTime.isPresent()) {
        g.drawString(Integer.toString(crossingTime.get()/5), tooltipX+10, tooltipY+60);
      } else {
        g.drawString("Cannot cross", tooltipX+10, tooltipY+60);
      }
    }
  }
}
